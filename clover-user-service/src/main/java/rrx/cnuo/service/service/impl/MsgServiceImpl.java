package rrx.cnuo.service.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.accessory.consts.Const.WeChatMsgEnum;
import rrx.cnuo.cncommon.util.ClientToolUtil;
import rrx.cnuo.cncommon.util.Validation;
import rrx.cnuo.cncommon.util.http.HttpClient;
import rrx.cnuo.cncommon.utils.RedisTool;
import rrx.cnuo.cncommon.utils.StarterToolUtil;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.config.BasicConfig;
import rrx.cnuo.cncommon.vo.config.WeChatMiniConfig;
import rrx.cnuo.service.accessory.config.MsgCenterConfigBean;
import rrx.cnuo.service.accessory.consts.MsgConst;
import rrx.cnuo.service.dao.MsgFormIdMapper;
import rrx.cnuo.service.dao.MsgSendCodeMapper;
import rrx.cnuo.service.dao.MsgWechatMapper;
import rrx.cnuo.service.po.MsgFormId;
import rrx.cnuo.service.po.MsgSendCode;
import rrx.cnuo.service.po.MsgWechat;
import rrx.cnuo.service.po.UserPassport;
import rrx.cnuo.service.service.MsgService;
import rrx.cnuo.service.service.data.UserPassportDataService;
import rrx.cnuo.service.utils.SMSUtils;
import rrx.cnuo.service.utils.SendMsgUtil;
import rrx.cnuo.service.vo.msgcenter.ReturnSmsMassegeVo;
import rrx.cnuo.service.vo.msgcenter.SmsMassegeVo;

@Service
public class MsgServiceImpl implements MsgService {

//	private static final String templateUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
	
    @Autowired private MsgWechatMapper msgWechatMapper;
    @Autowired private RedisTool instance;
    @Autowired private WeChatMiniConfig weChatMiniConfig;
    @Autowired private BasicConfig basicConfig;
    @Autowired private UserPassportDataService userPassportDataService;
    @Autowired private MsgSendCodeMapper msgSendCodeMapper;
    @Autowired private MsgFormIdMapper msgFormIdMapper;
    @Autowired private MsgCenterConfigBean msgCenterConfigBean;

    @Override
    public boolean updateSendMiniWxMsg(Long uid, WeChatMsgEnum noticeLenderReview, String msgVariableVal) throws Exception{
    	boolean isSuccess = true;
		try {
			saveMsgWechat(uid, noticeLenderReview.getCode(), msgVariableVal);
			MsgFormId formId = msgFormIdMapper.getFurthestFormId(uid);
			if(formId == null){
				return isSuccess;
			}
			
			UserPassport userPassport = userPassportDataService.selectByPrimaryKey(uid);
			String msgContent = noticeLenderReview.getMsgTemplate();
			if(StringUtils.isNotBlank(msgVariableVal) && msgContent.contains("%s")){
				Object[] arr = msgVariableVal.split("#");
				msgContent = String.format(msgContent, arr);
			}
			switch (noticeLenderReview) {
			case CREDIT_NOTIFY:
				SendMsgUtil.sendAuditNotify(instance,userPassport.getOpenId(),msgContent,noticeLenderReview.getPage(),formId.getFormId(),weChatMiniConfig);
				break;
			case RECEIVE_MONEY_NOTIFY:
				SendMsgUtil.sendIncomeToAccountNotify(instance,userPassport.getOpenId(), msgVariableVal, noticeLenderReview.getPage(),msgContent,formId.getFormId(),weChatMiniConfig);
				break;
			case BUSSINESS_PROGRESS:
				SendMsgUtil.taskHandleNotify(instance,userPassport.getOpenId(), noticeLenderReview.getTitle(),"成功", msgContent, noticeLenderReview.getPage(), formId.getFormId(),weChatMiniConfig);
				break;
			default:
				break;
			}
			msgFormIdMapper.deleteByPrimaryKey(formId.getId());
		} catch (Exception e) {
			isSuccess = false;
		}
		return isSuccess;
    }
    
    private void saveMsgWechat(Long uid,Byte msgType,String msgValue) throws Exception{
    	MsgWechat MsgWechat = new MsgWechat();
        MsgWechat.setUid(uid);
        MsgWechat.setMsgType(msgType);
        MsgWechat.setMsgValue(msgValue);
        MsgWechat.setId(StarterToolUtil.generatorLongId(instance));
        msgWechatMapper.insertSelective(MsgWechat);
    }

    @Override
    public JsonResult<ReturnSmsMassegeVo> updateSendSmsMessage(String requestIp, String telephone, int nType,String... args) throws Exception {
        JsonResult<ReturnSmsMassegeVo> result = new JsonResult<ReturnSmsMassegeVo>();
        result.setStatus(JsonResult.SUCCESS);
        
        if (!Validation.isMobileNO(telephone)) {
            result.setStatus(JsonResult.FAIL);
            result.setMsg("手机号不合法");
            return result;
        }
        // 检查此手机号短时间内获取验证码次数
        int codeNum = 0;
        String strTel = instance.getString(Const.REDIS_PREFIX.REDIS_TEL_NUM + telephone);
        if (strTel!=null) {
            codeNum = Integer.valueOf(strTel);
            if (codeNum >= Const.REDIS_PREFIX.REDIS_SMS_SEND_NUM) {
                result.setStatus(JsonResult.FAIL);
                result.setMsg("您在短时间内连续获取验证码已达到"+Const.REDIS_PREFIX.REDIS_SMS_SEND_NUM+"次，休息一下再试吧~");
                return result;
            }
        }

        //检查此ip一小时内发送短信次数
        int codeNumIp = 0;
        String strIpNum = instance.getString(Const.REDIS_PREFIX.REDIS_SMS_IP_NUM+requestIp);
        if (strIpNum!=null) {
            codeNumIp = Integer.valueOf(strIpNum);
            if (codeNumIp >= Const.REDIS_PREFIX.SMS_IP_LIMIT_PER_HOUR) {
                result.setStatus(JsonResult.FAIL);
                result.setMsg("您在短时间内连续获取验证码过于频繁，休息一下再试吧~");
                return result;
            }
        }
        // 发送验证码
        int code = SMSUtils.getRadomCode(basicConfig.isSendSMS());
        // 发送短信且数据库中插入发送记录
        result = sendSmsCodeResult(telephone, code, nType, args);
        if (result.getStatus() != JsonResult.SUCCESS) {
            return result;
        }

        // 将验证码发送次数和验证码信息放入redis
        String mobileCodeToken = ClientToolUtil.generateToken(Const.REDIS_PREFIX.REDIS_TEL, telephone);
        instance.set(mobileCodeToken, code + "", Const.REDIS_PREFIX.SMS_EXPIRE_TIME_SECONDS);

        instance.increase(Const.REDIS_PREFIX.REDIS_TEL_NUM + telephone);
        if(strTel==null){
            instance.expire(Const.REDIS_PREFIX.REDIS_TEL_NUM + telephone, Const.REDIS_PREFIX.SMS_CONTINUITY_SECONDS);
        }
        
        instance.increase(Const.REDIS_PREFIX.REDIS_SMS_IP_NUM+requestIp);
        if(strIpNum==null){
            instance.expire(Const.REDIS_PREFIX.REDIS_SMS_IP_NUM+requestIp, Const.REDIS_PREFIX.IP_LIMIT_EXPIRE_TIME_SECONDS);
        }

        return result;

    }

    /**
     * 发送短信且数据库中插入发送记录
     * @author xuhongyu
     * @param telephone
     * @param code
     * @param nType 0.注册验证码 1.申请贷款 2.修改交易密码 3.余额支付4.提现 5.找回密码 6.非注册获取验证码 7.原样发送8.充值9.重置密码10.好友找回
     * @param args
     * @return
     * @throws Exception
     */
    private JsonResult<ReturnSmsMassegeVo> sendSmsCodeResult(String telephone, Integer code, int nType, String... args) throws Exception {
        JsonResult<ReturnSmsMassegeVo> result = new JsonResult<ReturnSmsMassegeVo>();
        result.setStatus(JsonResult.FAIL);
        
        if (!basicConfig.isSendSMS()) {
            result.setStatus(JsonResult.SUCCESS);
            addSendCode(telephone, code, false, true);
            return result;
        }

        String msgContent = SMSUtils.getSendSMSContent(code, nType, args);
        if (StringUtils.isNotBlank(msgContent)) {
            SmsMassegeVo smsMassegeVo = new SmsMassegeVo();
            smsMassegeVo.setMsgContent(msgContent);
            smsMassegeVo.setMsgCode(code + "");
            smsMassegeVo.setMsgMobiles(telephone);
            smsMassegeVo.setMsgRouter(MsgConst.MsgRouter.MSG_NORMAL);
            smsMassegeVo.setMsgSmsType(MsgConst.MsgSmsType.TYPE_CODE);
            result = HttpClient.doPostWrapResult(msgCenterConfigBean.getBaseURL() + msgCenterConfigBean.getSmsSendUrl(), smsMassegeVo.toJSONString(), ReturnSmsMassegeVo.class);
            if (result.getStatus() == JsonResult.SUCCESS && result.getData().getRespCode() != JsonResult.SUCCESS) {
            	result.setStatus(result.getData().getRespCode());
            	result.setMsg(result.getData().getRespMsg());
            }
            addSendCode(telephone, code, false, result.getStatus() == JsonResult.SUCCESS ? true : false);
        }
        return result;
    }

    private void addSendCode(String telephone, Integer code, boolean voice, boolean success) {
        MsgSendCode ssgSendCode = new MsgSendCode();
        ssgSendCode.setTelephone(telephone);
        ssgSendCode.setCode(code);
        ssgSendCode.setVoiceStatus(voice);
        ssgSendCode.setState(success);
        msgSendCodeMapper.insertSelective(ssgSendCode);
    }

    
    
    /**
     * 根据消息类型发送微信(公众号)消息
     * @author xuhongyu
     */
    /*private void sendWeChatTemplateMSG(MsgVo msgVo) throws Exception {
    	if (msgVo != null) {
            String msgContent = "";
            String redirectUrl = ToolUtil.getRedirectUrl(msgVo.getShortUrl());
            // 发送微信模板消息
            WeiXinTemplate w = new WeiXinTemplate(msgVo.getOpenId(), redirectUrl);
            WeixinMsgTemplate st = new WeixinMsgTemplate();
            st.setFirst(new ValueColor(msgVo.getFirst()));
            st.setKeyword1(new ValueColor(msgVo.getValue1()));
            st.setKeyword2(new ValueColor(msgVo.getValue2()));
            st.setKeyword3(new ValueColor(msgVo.getValue3()));
            st.setKeyword4(new ValueColor(msgVo.getValue4()));
            st.setKeyword5(new ValueColor(msgVo.getValue5()));
            st.setRemark(new ValueColor(msgVo.getRemark()));
            msgContent = st.toSendMessage(w, msgVo.getSendMsgType());
            
            //保存微信消息
            saveWeChatMsgByType(msgVo);
            
            // 发送微信消息
            JSONObject msg = new JSONObject();
            String token = AccessToken.getToken(instance, false);
            String url = templateUrl + token;
            msg.put("msg_type", "weChat");
            msg.put("msg_channel", "JJD");
            msg.put("msg_url", url);
            msg.put("msg_content", msgContent);
            //log.info("=============发送微信消息================" + msg.toJSONString());
            HttpClient.doPostWrapResult(ConfigBean.getWechatSendUrl(), msg.toJSONString(), null);
        }
    }*/

    /**
     * 根据消息类型保存数据库
     * @param msgVo
     * @throws Exception
     */
    /*private void saveWeChatMsgByType(MsgVo msgVo) throws Exception {
        if (msgVo != null) {
            JSONObject vo = new JSONObject();
            vo.put("title", msgVo.getTitle());
            vo.put("url", msgVo.getShortUrl());
            vo.put("first", msgVo.getFirst());
            vo.put("reMark", msgVo.getRemark());
            vo.put("paramValue", msgVo.getParamValue());
            vo.put("contentValue", msgVo.getContentValue());
            vo.put("isEncryptID", msgVo.getIsEncryptID());
            if (StringUtils.isNotBlank(msgVo.getKey1())) {
                vo.put("key1", msgVo.getKey1());
                vo.put("value1", msgVo.getValue1());
            }
            if (StringUtils.isNotBlank(msgVo.getKey2())) {
                vo.put("key2", msgVo.getKey2());
                vo.put("value2", msgVo.getValue2());
            }
            if (StringUtils.isNotBlank(msgVo.getKey3())) {
                vo.put("key3", msgVo.getKey3());
                vo.put("value3", msgVo.getValue3());
            }
            if (StringUtils.isNotBlank(msgVo.getKey4())) {
                vo.put("key4", msgVo.getKey4());
                vo.put("value4", msgVo.getValue4());
            }
            if (StringUtils.isNotBlank(msgVo.getKey5())) {
                vo.put("key5", msgVo.getKey5());
                vo.put("value5", msgVo.getValue5());
            }

            //保存数据库
            MsgWechat MsgWechat = new MsgWechat();
            MsgWechat.setUid(msgVo.getUserId());
            MsgWechat.setMsgType((byte) msgVo.getSaveMsgType());
            MsgWechat.setMsg(vo.toJSONString());
            msgWechatMapper.insertSelective(MsgWechat);
        }
    }*/
}
