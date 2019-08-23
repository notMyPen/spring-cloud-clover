package rrx.cnuo.service.service.impl.data;

import java.util.List;

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
		return getUserPassportByKey(userId + "", (byte) 2);
	}
	
	@Override
	public UserPassport selectByMiniOpenid(String miniOpenId) throws Exception{
		return getUserPassportByKey(miniOpenId, (byte) 1);
	}
	
	/**
	 * 根据key获取UserPassport
	 * @author xuhongyu
	 * @param key
	 * @param keyType key类型：1-微信小程序openId；2--uid
	 * @return
	 */
	private UserPassport getUserPassportByKey(String key,byte keyType) throws Exception{
		String redisKey  = Const.REDIS_PREFIX.REDIS_USER_PASSPORT + key;
		String str = redis.getString(redisKey);
		UserPassport userPassport = null;
        if (StringUtils.isBlank(str)) {
        	if(keyType == 1) {
        		UserPassport param = new UserPassport();
        		param.setMiniOpenId(key);
        		List<UserPassport> userPassports = userPassportMapper.selectByParam(param);
        		if(userPassports != null && userPassports.size() > 0) {
        			userPassport = userPassports.get(0);
        		}
        	}else {
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

	@Override
	public void insertSelective(UserPassport userPassport) {
		userPassportMapper.insertSelective(userPassport);
	}

}
