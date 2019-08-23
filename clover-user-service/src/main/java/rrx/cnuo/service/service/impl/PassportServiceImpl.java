package rrx.cnuo.service.service.impl;

import java.util.Base64;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.util.ClientToolUtil;
import rrx.cnuo.cncommon.util.EncryptUtil;
import rrx.cnuo.cncommon.util.RandomGenerator;
import rrx.cnuo.cncommon.utils.RedisTool;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.config.BasicConfig;
import rrx.cnuo.cncommon.vo.config.WeChatMiniConfig;
import rrx.cnuo.service.feignclient.OrderFeignService;
import rrx.cnuo.service.po.UserAccount;
import rrx.cnuo.service.po.UserBasicInfo;
import rrx.cnuo.service.po.UserPassport;
import rrx.cnuo.service.service.PassportService;
import rrx.cnuo.service.service.data.UserAccountDataService;
import rrx.cnuo.service.service.data.UserBasicInfoService;
import rrx.cnuo.service.service.data.UserPassportDataService;
import rrx.cnuo.service.utils.AccessToken;
import rrx.cnuo.service.vo.passport.external.WxMiniUserInfoModel;
import rrx.cnuo.service.vo.passport.request.OauthParam;
import rrx.cnuo.service.vo.passport.response.UserInitOauthVo;

/**
 * 处理用户登录相关操作
 * @author xuhongyu
 * @date 2019年6月26日
 */
@Slf4j
@Service
@SuppressWarnings("unchecked")
public class PassportServiceImpl implements PassportService {

	@Autowired private RedisTool redis;
	@Autowired private UserBasicInfoService userBasicInfoService;
	@Autowired private WeChatMiniConfig weChatMiniConfig;
	@Autowired private UserAccountDataService userAccountDataService;
	@Autowired private UserPassportDataService userPassportDataService;
	@Autowired private OrderFeignService orderFeignService;
	@Autowired private BasicConfig basicConfig;
	
	/**
	 * 注册新用户
	 * @author xuhongyu
	 * @param openId
	 * @return
	 */
	private Long register(WxMiniUserInfoModel wxUserInfoModel) throws Exception{
		UserPassport userPassport = new UserPassport();
		userPassport.setUid(ClientToolUtil.getDistributedId(basicConfig.getSnowflakeNode()));
		//TODO 存到阿里云
		userPassport.setAvatarUrl(wxUserInfoModel.getAvatarUrl());
		userPassport.setNickName(wxUserInfoModel.getNickName());
		userPassport.setMiniOpenId(wxUserInfoModel.getOpenId());
		userPassportDataService.insertSelective(userPassport);
    	
    	UserAccount userAccount = new UserAccount();
    	userAccount.setUid(userPassport.getUid());
    	userAccountDataService.insertSelective(userAccount);
    	
    	UserBasicInfo userBasicInfo = new UserBasicInfo();
    	userBasicInfo.setUid(userPassport.getUid());
    	userBasicInfoService.insertSelective(userBasicInfo);
    	
    	//TODO user_detail_info
		return userPassport.getUid();
	}
	
	private JsonResult<UserInitOauthVo> getUserInitOauthVo(String openId,Long uid) throws Exception{
		JsonResult<UserInitOauthVo> result = new JsonResult<>();
        result.setStatus(JsonResult.SUCCESS);
        
        //TODO 用gateway中的
		String ticket = getTicketFromUserId(uid + "");
        if (StringUtils.isBlank(ticket)) {
            log.error("getTicketFromUserId fail, null ticket, userId:{}", uid);
            result.setStatus(JsonResult.FAIL);
            result.setMsg("获取ticket失败");
            return result;
        }
        UserInitOauthVo userWxInfo = new UserInitOauthVo();
        userWxInfo.setMiniOpenId(openId);
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

	@Override
	public JsonResult<UserInitOauthVo> updateOauth(OauthParam oauthParam) throws Exception{
        if (StringUtils.isBlank(oauthParam.getCode())) {
            return JsonResult.fail(JsonResult.FAIL, "微信code不能为空");
        }
        
        JsonResult<WxMiniUserInfoModel> oauthResult = wxOauth(oauthParam);
        if(!oauthResult.isOk()) {
        	return JsonResult.fail(oauthResult.getStatus(), oauthResult.getMsg());
        }
        WxMiniUserInfoModel wxUserInfoModel = oauthResult.getData();
        
        UserPassport userPassport = userPassportDataService.selectByMiniOpenid(wxUserInfoModel.getOpenId());
        Long uid = null;
        if(userPassport == null){//未注册
        	uid = register(wxUserInfoModel);
        	//支付中心开户
        	if(orderFeignService.openAccountBalance(uid).getData()){
        		UserAccount userBasicParam = new UserAccount();
        		userBasicParam.setUid(uid);
        		userBasicParam.setOpenAccount(true);
        		userAccountDataService.updateByPrimaryKeySelective(userBasicParam);
        	}
        }else{//已注册(暂不支持用户用手机号登录更换微信公众号登录)
        	uid = userPassport.getUid();
        }
        return getUserInitOauthVo(wxUserInfoModel.getOpenId(), uid);
	}
	
	/**
	 * 微信授权验证
	 * @author xuhongyu
	 * @param code
	 * @return
	 */
	private JsonResult<WxMiniUserInfoModel> wxOauth(OauthParam oauthParam) throws Exception{
		// 根据mySign和Signature进行第一次校验
        JSONObject tokenJson = AccessToken.getWxSessionJsonByCode(oauthParam.getCode(),weChatMiniConfig);
        String wx_session_key = tokenJson.getString("session_key");
        String mySign = EncryptUtil.sha1(oauthParam.getRawData() + wx_session_key);
        if (!oauthParam.getSignature().equals(mySign)) {
            return JsonResult.fail(JsonResult.AUTHORIZE_ERROR, "微信授权错误");
        }
        
        //根据appid是否一致进行第二次校验
        byte[] sessionKeyCla = Base64.getDecoder().decode(wx_session_key);
        byte[] encryptedDataCla = Base64.getDecoder().decode(oauthParam.getEncryptedData());
        byte[] ivCla = Base64.getDecoder().decode(oauthParam.getIv());
        String userInfo = EncryptUtil.aes_decrypt(sessionKeyCla,ivCla, encryptedDataCla);
        JSONObject jsonObject = JSON.parseObject(userInfo);
        if (jsonObject == null || !jsonObject.containsKey("watermark")
                || null==jsonObject.getJSONObject("watermark")
                || !jsonObject.getJSONObject("watermark").containsKey("appid")
                || !weChatMiniConfig.getMiniAppId().equals(jsonObject.getJSONObject("watermark").getString("appid"))) {
        	return JsonResult.fail(JsonResult.AUTHORIZE_ERROR, "微信授权错误");
        }
        WxMiniUserInfoModel wxUserInfo = JSON.parseObject(userInfo, WxMiniUserInfoModel.class);
        if (StringUtils.isBlank(wxUserInfo.getOpenId())) {
        	return JsonResult.fail(JsonResult.FAIL, "获取微信openid失败，请稍后重试!");
        }
        return JsonResult.ok(wxUserInfo);
	}
}
