package rrx.cnuo.service.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.util.http.HttpClient;
import rrx.cnuo.cncommon.util.http.HttpsClient;
import rrx.cnuo.cncommon.utils.RedisTool;
import rrx.cnuo.cncommon.vo.config.WeChatAppConfig;
import rrx.cnuo.cncommon.vo.config.WeChatMiniConfig;
import rrx.cnuo.service.weixin.model.AccessTokenBean;
import rrx.cnuo.service.weixin.model.CodeResponse;
import rrx.cnuo.service.weixin.model.WeiXinUserinfo;

/**
 * accessToken工具类
 * @author xuhongyu
 * @date 2019年3月29日
 */
@Slf4j
public class AccessToken {
    private static final String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=";
    private static final String miniOpenIdUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=";
    private static final String wechatOpenIdUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=";
    private static final String weiXinUserInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=";
    private static final String weiXinUserInfoTempUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=";
    
    //获取微信小程序码url
    private static final String wechatAppletCodeUrl = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";
    
    /**
     * 从redis中获取token，如果redis中token未失效就取redis中的值，否则获取分布式自旋锁从微信获取token(用户的共享数据)并将其放进redis中。
     * 如果获取不到redis连接抛出异常不去微信获取新token
     * @author xuhongyu
     * @param instance
     * @return
     * @throws Exception
     * String
     */
    public static String getToken(RedisTool instance,String appid,String appSecret) throws Exception {
    	String token = null;
        if (instance.checkKeyExisted(appid)) {
        	//redis中Token存在
        	token = getTokenFromRedis(instance, appid);
        }else{
        	String requestId = UUID.randomUUID().toString();//建锁请求标识
        	if(instance.getDistributedSpinLock(Const.REDIS_PREFIX.REDIS_LOCK_KEY_PREFIX + appid, requestId)){
        		if (instance.checkKeyExisted(appid)) {
        			token = getTokenFromRedis(instance, appid);
        		}else{
        			token = getTokenFromWechat(instance,appid,appSecret);
        		}
        		instance.releaseDistributedLock(Const.REDIS_PREFIX.REDIS_LOCK_KEY_PREFIX + appid, requestId);
        	}//TODO else{}此处如果并发量高可使用分布式互斥锁+二级缓存：当未获取到互斥锁直接去获取二级缓存数据
        }
        return token;
    }
    
    private static String getTokenFromRedis(RedisTool instance,String appid) throws Exception{
    	String redisTokenStr = (String) instance.getString(appid);
    	AccessTokenBean accessTokenBean = JSONObject.parseObject(redisTokenStr, AccessTokenBean.class);
    	return accessTokenBean.getAccess_token();
    }
    
    private static String getTokenFromWechat(RedisTool instance,String appid,String appSecret) throws Exception{
		String url = tokenUrl + appid + "&secret=" + appSecret;
    	for (int i = 0; i < 3; i++) {
			try {
				String tokenStr = HttpsClient.doGet(url);
				log.info("=========获取token值："+tokenStr);
				AccessTokenBean accessToken = JSON.parseObject(tokenStr, AccessTokenBean.class);
				if(accessToken == null || accessToken.getExpires_in()==0){
					continue;
				}
				instance.set(appid, JSON.toJSONString(accessToken), Integer.valueOf(Const.REDIS_PREFIX.ACCESS_TOKEN_EXPIRE));
				return accessToken.getAccess_token();
			} catch (Exception e) {
				log.error("去微信获取accessToken异常：",e);
			}
		}
    	return null;
    }
    
    public static String getMiniOpenidByCode(String code,WeChatMiniConfig weChatMiniConfig) throws Exception {
        //通过code获取用户在应用appid下唯一标识openid
        String url = miniOpenIdUrl + weChatMiniConfig.getMiniAppId() + "&secret=" + weChatMiniConfig.getMiniAppSecret()
                + "&js_code=" + code + "&grant_type=authorization_code";
        JSONObject respJson = null;
        String resp = null;
        for(int i = 0; i < 3; i++){
        	resp = HttpsClient.doGet(url);
        	if (StringUtils.isBlank(resp)) {
        		continue;
        	}
        	respJson = JSONObject.parseObject(resp);
        	log.info("======getOpenidByCode方法的result："+respJson.toJSONString());
        	if (respJson == null || StringUtils.isBlank(respJson.getString("openid"))) {
        		continue;
        	}
        	return respJson.getString("openid");
        }
        return null;
    }
    
    /**
     * 获取微信公众号信息
     * @author xuhongyu
     * @param instance
     * @param code
     * @param weChatAppConfig
     * @return
     * @throws Exception
     */
    public static WeiXinUserinfo getWechatUserInfo(RedisTool instance, String code,WeChatAppConfig weChatAppConfig) throws Exception {
        //通过code换取网页授权access_token
        String getOpenIdUrl = wechatOpenIdUrl + weChatAppConfig.getWechatAppid() + "&secret=" + weChatAppConfig.getWechatAppsecret()
                + "&code=" + code + "&grant_type=authorization_code";
        CodeResponse codeResponse = null;
        for (int i = 0; i < 5; i++) {
            try {
            	String codeResponseStr = HttpsClient.doGet(getOpenIdUrl);
            	if(StringUtils.isNotBlank(codeResponseStr)){
            		codeResponse = JSON.parseObject(codeResponseStr, CodeResponse.class);
            		log.info("========="+code+"的codeResponseStr:========"+codeResponseStr);
            		break;
            	}
            } catch (Exception e) {
            	log.error("获取codeResponse异常", e);
            }
        }
        if (codeResponse == null || StringUtils.isBlank(codeResponse.openid)) {
            return null;
        }
        String openId = codeResponse.openid;
        //获取用户基本信息（包括UnionID机制）
        String access_token = getToken(instance,weChatAppConfig.getWechatAppid(),weChatAppConfig.getWechatAppsecret());
        String wxInfoUrl = weiXinUserInfoUrl + access_token + "&openid=" + openId + "&lang=zh_CN";
        WeiXinUserinfo weiXinUserinfo = null;
        for (int i = 0; i < 5; i++) {
            try {
            	String weiXinUserinfoStr = HttpsClient.doGet(wxInfoUrl);
            	if(StringUtils.isNotBlank(weiXinUserinfoStr)){
            		weiXinUserinfo = JSON.parseObject(weiXinUserinfoStr, WeiXinUserinfo.class);
            		log.info("=============weiXinUserinfoStr:========="+weiXinUserinfoStr);
                    break;
            	}
            } catch (Exception e) {
            	log.error("获取weiXinUserinfo异常", e);
            }
        }
        
        if (weiXinUserinfo != null && weiXinUserinfo.getSubscribe() != null && weiXinUserinfo.getSubscribe() == 0) {
        	//如果未关注,用另一个url获取微信用户信息
            String wxInfoTempUrl = weiXinUserInfoTempUrl + codeResponse.access_token + "&openid=" + openId + "&lang=zh_CN";
            WeiXinUserinfo weiXinUserinfoTemp = null;
            for (int i = 0; i < 5; i++) {
                try {
                	String weiXinUserinfoTempStr = HttpsClient.doGet(wxInfoTempUrl);
                	if(StringUtils.isNotBlank(weiXinUserinfoTempStr)){
                		log.info("=============weiXinUserinfoTempStr:========="+weiXinUserinfoTempStr);
                		weiXinUserinfoTemp = JSON.parseObject(weiXinUserinfoTempStr, WeiXinUserinfo.class);
                        break;
                	}
                } catch (Exception e) {
                	log.error("获取weiXinUserinfoTemp异常", e);
                }
            }
            BeanUtils.copyProperties(weiXinUserinfoTemp, weiXinUserinfo);
            weiXinUserinfo.setSubscribe(0);
        }
        
        if(weiXinUserinfo == null){
        	weiXinUserinfo = new WeiXinUserinfo();
        	weiXinUserinfo.setOpenid(openId);
        }else{
        	if(StringUtils.isBlank(weiXinUserinfo.getOpenid())){
        		weiXinUserinfo.setOpenid(openId);
        	}
        }
        return weiXinUserinfo;
    }
    
    /**
     * 获取小程序码
     */
    public static byte[] createMiniProgramCode(RedisTool redis, String scene, String page,WeChatMiniConfig weChatMiniConfig) throws Exception {
        String token = getToken(redis,weChatMiniConfig.getMiniAppId(),weChatMiniConfig.getMiniAppSecret());
        Map<String, Object> params = new HashMap<>();
        params.put("scene", scene);
        params.put("width", 300);
        if (StringUtils.isNotBlank(page)) {
            params.put("page", page);
        }
        return HttpClient.doImgPost(wechatAppletCodeUrl + token, params);
    }
}
