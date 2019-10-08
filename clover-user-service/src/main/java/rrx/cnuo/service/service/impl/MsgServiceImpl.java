package rrx.cnuo.service.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.accessory.consts.Const.WeChatMsgEnum;
import rrx.cnuo.cncommon.accessory.context.UserContextHolder;
import rrx.cnuo.cncommon.util.ClientToolUtil;
import rrx.cnuo.cncommon.utils.MqSendTool;
import rrx.cnuo.cncommon.utils.RedisTool;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.config.BasicConfig;
import rrx.cnuo.service.accessory.config.WeChatMiniConfigBean;
import rrx.cnuo.service.accessory.consts.MsgConst;
import rrx.cnuo.service.accessory.consts.UserConst;
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
import rrx.cnuo.service.utils.Validation;
import rrx.cnuo.service.vo.msgcenter.SmsMassegeVo;

@Service
@SuppressWarnings("rawtypes")
public class MsgServiceImpl implements MsgService {

//	private static final String templateUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
	
    @Autowired private MsgWechatMapper msgWechatMapper;
    @Autowired private RedisTool instance;
    @Autowired private WeChatMiniConfigBean weChatMiniConfigBean;
    @Autowired private BasicConfig basicConfig;
    @Autowired private UserPassportDataService userPassportDataService;
    @Autowired private MsgSendCodeMapper msgSendCodeMapper;
    @Autowired private MsgFormIdMapper msgFormIdMapper;
    @Autowired private MsgFormIdMapper msgformIdMapper;
    @Autowired private MqSendTool mqSendTool;

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
			case CREDIT_FEE_PAIED:
				SendMsgUtil.sendAuditNotify(instance,userPassport.getMiniOpenId(),msgContent,noticeLenderReview.getPage(),formId.getFormId(),weChatMiniConfigBean);
				break;
			case CREDIT_COMPLETE:
				SendMsgUtil.sendIncomeToAccountNotify(instance,userPassport.getMiniOpenId(), msgVariableVal, noticeLenderReview.getPage(),msgContent,formId.getFormId(),weChatMiniConfigBean);
				break;
			case BUYCARD_PAIED:
				SendMsgUtil.taskHandleNotify(instance,userPassport.getMiniOpenId(), noticeLenderReview.getTitle(),"成功", msgContent, noticeLenderReview.getPage(), formId.getFormId(),weChatMiniConfigBean);
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
        MsgWechat.setId(ClientToolUtil.getDistributedId(basicConfig.getSnowflakeNode()));
        msgWechatMapper.insertSelective(MsgWechat);
    }

    @Override
    public JsonResult updateSendSmsMessage(String requestIp, String telephone, int nType,String... args) throws Exception {
        JsonResult result = new JsonResult();
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
        sendSmsCodeResult(telephone, code, nType, args);

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
    private void sendSmsCodeResult(String telephone, Integer code, int nType, String... args) throws Exception {
        if (basicConfig.isSendSMS()) {
        	String msgContent = SMSUtils.getSendSMSContent(code, nType, args);
        	SmsMassegeVo smsMassegeVo = new SmsMassegeVo();
        	smsMassegeVo.setMsgContent(msgContent);
        	smsMassegeVo.setMsgCode(code + "");
        	smsMassegeVo.setMsgMobiles(telephone);
        	smsMassegeVo.setMsgRouter(MsgConst.MsgRouter.MSG_NORMAL);
        	smsMassegeVo.setMsgSmsType(MsgConst.MsgSmsType.TYPE_CODE);
        	JSONObject msg = JSONObject.parseObject(smsMassegeVo.toJSONString());
        	mqSendTool.smsWxMqSender(msg);
        }
        addSendCode(telephone, code, false, true);
    }

    private void addSendCode(String telephone, Integer code, boolean voice, boolean success) {
        MsgSendCode ssgSendCode = new MsgSendCode();
        ssgSendCode.setTelephone(telephone);
        ssgSendCode.setCode(code);
        ssgSendCode.setVoiceStatus(voice);
        ssgSendCode.setState(success);
        msgSendCodeMapper.insertSelective(ssgSendCode);
    }

    @Override
    public void removeExpiredFormid() throws Exception {
        long expireTime = System.currentTimeMillis() + Const.REDIS_PREFIX.CONFIRM_EXPIRE;
        msgformIdMapper.removeExpiredFormid(expireTime);
    }
	
	@Override
    public JsonResult saveFormId(String formId) throws Exception {
        JsonResult result = new JsonResult();
        result.setStatus(JsonResult.SUCCESS);
        
        if (StringUtils.isBlank(formId) || formId.contains("mock")) {
        	return result;
        }
        
        Long uid = UserContextHolder.currentUser().getUserId();
        int count = msgformIdMapper.countByUid(uid);
        if (count >= UserConst.USER_MAX_FORMID_CNT) {
            return result;
        }
        MsgFormId formid = new MsgFormId();
        formid.setUid(uid);
        formid.setFormId(formId);
        formid.setExpireTime(System.currentTimeMillis() + Const.REDIS_PREFIX.FORMID_EXPIRE);
        msgformIdMapper.insertSelective(formid);
        return result;
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
