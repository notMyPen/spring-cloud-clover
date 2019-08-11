package rrx.cnuo.service.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.util.EncryptUtil;
import rrx.cnuo.cncommon.util.RandomGenerator;
import rrx.cnuo.cncommon.util.RedisTool;
import rrx.cnuo.cncommon.util.ToolUtil;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.UserInitOauthVo;
import rrx.cnuo.cncommon.vo.config.BasicConfig;
import rrx.cnuo.cncommon.vo.config.WeChatAppConfig;
import rrx.cnuo.cncommon.vo.config.WeChatMiniConfig;
import rrx.cnuo.service.dao.UserAccountMapper;
import rrx.cnuo.service.dao.UserPassportMapper;
import rrx.cnuo.service.feignclient.OrderFeignService;
import rrx.cnuo.service.po.UserAccount;
import rrx.cnuo.service.po.UserPassport;
import rrx.cnuo.service.service.PassportService;
import rrx.cnuo.service.service.data.UserAccountDataService;
import rrx.cnuo.service.service.data.UserPassportDataService;
import rrx.cnuo.service.utils.AccessToken;
import rrx.cnuo.service.weixin.model.WeiXinUserinfo;

/**
 * 处理用户登录相关操作
 * @author xuhongyu
 * @date 2019年6月26日
 */
@Slf4j
@Service
public class PassportServiceImpl implements PassportService {

	@Autowired private RedisTool redis;
	@Autowired private WeChatAppConfig weChatAppConfig;
	@Autowired private WeChatMiniConfig weChatMiniConfig;
	@Autowired private UserPassportMapper userPassportMapper;
	@Autowired private UserAccountMapper userAccountMapper;
	@Autowired private UserAccountDataService userAccountDataService;
	@Autowired private UserPassportDataService userPassportDataService;
	@Autowired private OrderFeignService orderFeignService;
	@Autowired private BasicConfig basicConfig;
	
	@Override
	public JsonResult<UserInitOauthVo> updateInit(String code,Byte platform) throws Exception{
		JsonResult<UserInitOauthVo> result = new JsonResult<>();
        result.setStatus(JsonResult.SUCCESS);
        
        if (StringUtils.isBlank(code)) {
        	result.setStatus(JsonResult.FAIL);
            result.setMsg("微信code不能为空");
            return result;
        }
        if (platform != Const.Platform.WECHAT.getCode() && platform != Const.Platform.WX_MINI.getCode()) {
        	result.setStatus(JsonResult.FAIL);
        	result.setMsg("参数platform传递错误，请在微信公众号或微信小程序中使用该init方法");
        	return result;
        }
        WeiXinUserinfo weiXinUserinfo = null;
        UserPassport userPassport = null;
        // 查询微信信息
        if(platform == Const.Platform.WECHAT.getCode()){
        	weiXinUserinfo = AccessToken.getWechatUserInfo(redis, code,weChatAppConfig);
     		if (weiXinUserinfo == null || weiXinUserinfo.getOpenid() == null) {
     			result.setStatus(JsonResult.FAIL);
     			result.setMsg("微信出了一些小故障，请退出页面，重新进入");
     			return result;
     		}
     		userPassport = userPassportDataService.selectByOpenid(weiXinUserinfo.getOpenid());
        }else{
        	String openId = AccessToken.getMiniOpenidByCode(code,weChatMiniConfig);
        	if (StringUtils.isBlank(openId)) {
        		result.setStatus(JsonResult.FAIL);
        		result.setMsg("获取微信openid失败，请稍后重试!");
        		return result;
        	}
        	weiXinUserinfo = new WeiXinUserinfo();
        	weiXinUserinfo.setOpenid(openId);
            userPassport = userPassportDataService.selectByMiniOpenid(openId);
        }
        Long uid = null;
        if(userPassport == null){//未注册
        	uid = register(weiXinUserinfo,platform);
        	//支付中心开户
        	if(orderFeignService.openAccountBalance(uid).getData()){
        		UserAccount userBasicParam = new UserAccount();
        		userBasicParam.setUid(uid);
        		userBasicParam.setOpenAccount(true);
        		userAccountDataService.updateByPrimaryKeySelective(userBasicParam);
        	}
        }else{//已注册(暂不支持用户用手机号登录更换微信公众号登录)
        	uid = userPassport.getUid();
        	if(platform == Const.Platform.WECHAT.getCode()){
        		Boolean subscribe = weiXinUserinfo.getSubscribe() == 0 ? false : true;//值为0时，代表此用户没有关注该公众号。 
        		if(!userPassport.getSubscribe().equals(subscribe)){//如果关注状态变了，更新
        			UserPassport param = new UserPassport();
        			param.setUid(uid);
        			param.setSubscribe(subscribe);
        			userPassportDataService.updateByPrimaryKeySelective(param);
        		}
        	}
        }
        result = getUserInitOauthVo(weiXinUserinfo.getOpenid(), uid);
		return result;
	}
	
	/**
	 * 注册新用户
	 * @author xuhongyu
	 * @param openId
	 * @return
	 */
	private Long register(WeiXinUserinfo weiXinUserinfo,Byte platform) {
		UserPassport userPassport = new UserPassport();
		userPassport.setUid(ToolUtil.generatorLongId(redis));
		if(platform == Const.Platform.WECHAT.getCode()){//微信公众号
			userPassport.setOpenId(weiXinUserinfo.getOpenid());
			userPassport.setAvatarUrl(weiXinUserinfo.getHeadimgurl());
			userPassport.setNickName(weiXinUserinfo.getNickname());
			userPassport.setSubscribe(weiXinUserinfo.getSubscribe() == 0 ? false : true);
		}else{//微信小程序
			userPassport.setMiniOpenId(weiXinUserinfo.getOpenid());
		}
    	userPassportMapper.insertSelective(userPassport);
    	
    	UserAccount userAccount = new UserAccount();
    	userAccount.setUid(userPassport.getUid());
    	userAccountMapper.insertSelective(userAccount);
    	
		// user_basic_info表字段不确定，确认后再加入
    	
		return userPassport.getUid();
	}
	
	private JsonResult<UserInitOauthVo> getUserInitOauthVo(String openId,Long uid) throws Exception{
		JsonResult<UserInitOauthVo> result = new JsonResult<>();
        result.setStatus(JsonResult.SUCCESS);
        
		String ticket = getTicketFromUserId(uid + "");
        if (StringUtils.isBlank(ticket)) {
            log.error("getTicketFromUserId fail, null ticket, userId:{}", uid);
            result.setStatus(JsonResult.FAIL);
            result.setMsg("获取ticket失败");
            return result;
        }
        UserInitOauthVo userWxInfo = new UserInitOauthVo();
        userWxInfo.setOpenId(openId);
        userWxInfo.setUid(uid);
        userWxInfo.setTicket(ticket);
        userWxInfo.setExpireTime(Const.TICKET_EXPIRE);
        result.setData(userWxInfo);
        return result;
	}
	
	private String getTicketFromUserId(String userId) throws Exception{
		String ivParameter = RandomGenerator.generateRandomString(Const.IVPARAMETER_LENGTH);
        String ticket = ivParameter + EncryptUtil.aes_encrypt(basicConfig.getAesEncryptKey(), ivParameter, userId);
        
        redis.set(Const.REDIS_PREFIX.TICKET_FILE + ticket, userId, Const.TICKET_EXPIRE);
        return ticket;
    }
}
