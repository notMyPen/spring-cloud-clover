package rrx.cnuo.service.service.impl.data;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.util.SimplifyObjJsonUtil;
import rrx.cnuo.cncommon.utils.RedisTool;
import rrx.cnuo.service.dao.UserPassportMapper;
import rrx.cnuo.service.po.UserPassport;
import rrx.cnuo.service.service.data.UserPassportDataService;

@Service
public class UserPassportDataServiceImpl implements UserPassportDataService {
	
	@Autowired
    private RedisTool redis;
	
	@Autowired
	private UserPassportMapper userPassportMapper;

	@Override
	public UserPassport selectByPrimaryKey(long userId) throws Exception {
		return getUserPassportByKey(userId + "", (byte) 3);
	}
	
	@Override
	public UserPassport selectByOpenid(String openId) throws Exception{
		return getUserPassportByKey(openId, Const.Platform.WECHAT.getCode());
	}

	@Override
	public UserPassport selectByMiniOpenid(String miniOpenId) throws Exception{
		return getUserPassportByKey(miniOpenId, Const.Platform.WX_MINI.getCode());
	}
	
	/**
	 * 根据key获取UserPassport
	 * @author xuhongyu
	 * @param key
	 * @param keyType key类型：1-微信公众号openId；2-微信小程序openId；3-uid
	 * @return
	 */
	private UserPassport getUserPassportByKey(String key,byte keyType) throws Exception{
		String redisKey  = Const.REDIS_PREFIX.REDIS_USER_PASSPORT + key;
		String str = redis.getString(redisKey);
		UserPassport userPassport = null;
        if (StringUtils.isBlank(str)) {
        	if(keyType == Const.Platform.WECHAT.getCode()){
        		userPassport = userPassportMapper.selectByWxOpenId(key);
        	}else if(keyType == Const.Platform.WX_MINI.getCode()){
        		userPassport = userPassportMapper.selectByWxMiniOpenId(key);
        	}else{
        		userPassport = userPassportMapper.selectByPrimaryKey(Long.parseLong(key));
        	}
            if (userPassport != null) {
            	JSONObject userJson = SimplifyObjJsonUtil.getSimplifyJsonObjFromOriginObj(userPassport, SimplifyObjJsonUtil.userPassportSimplifyTemplate);
                redis.set(redisKey, userJson.toJSONString(), Const.REDIS_PREFIX.USER_INFO_SECONDS);
            }else{
            	//如果从mysql中查询的UserPassport为空，在redis中赋值“null”，防止缓存穿透
            	redis.set(redisKey, "null", Const.REDIS_PREFIX.USER_INFO_SECONDS);
            }
        } else {
        	if(!"null".equals(str)){
        		userPassport = SimplifyObjJsonUtil.getOriginObjFromSimplifyJsonStr(str, UserPassport.class, SimplifyObjJsonUtil.userPassportRestoreTemplate);
        	}
        }
        return userPassport;
	}

	@Override
	public void delUserAccountFromRedis(long uid) throws Exception {
		redis.delete(Const.REDIS_PREFIX.REDIS_USER_PASSPORT + uid);
	}

	@Override
	public void updateByPrimaryKeySelective(UserPassport param) throws Exception {
		userPassportMapper.updateByPrimaryKeySelective(param);
		delUserAccountFromRedis(param.getUid());
	}

}
