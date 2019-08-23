package rrx.cnuo.service.service.impl.data;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.util.SimplifyObjJsonUtil;
import rrx.cnuo.cncommon.utils.RedisTool;
import rrx.cnuo.service.dao.UserBasicInfoMapper;
import rrx.cnuo.service.po.UserBasicInfo;
import rrx.cnuo.service.service.data.UserBasicInfoService;

@Service
public class UserBasicInfoServiceImpl implements UserBasicInfoService {

	@Autowired private UserBasicInfoMapper userBasicInfoMapper;
	@Autowired private RedisTool redis;

	@Override
	public void insertSelective(UserBasicInfo record) throws Exception{
		userBasicInfoMapper.insertSelective(record);
	}

	@Override
	public UserBasicInfo selectByPrimaryKey(Long userId) throws Exception{
		String redisKey  = Const.REDIS_PREFIX.REDIS_USER_BASIC_INFO + userId;
		String str = redis.getString(redisKey);
		UserBasicInfo userBasicInfo = null;
        if (StringUtils.isBlank(str)) {
            userBasicInfo = userBasicInfoMapper.selectByPrimaryKey(userId);
            if (userBasicInfo != null) {
            	JSONObject userJson = SimplifyObjJsonUtil.getSimplifyJsonObjFromOriginObj(userBasicInfo, SimplifyObjJsonUtil.userAccountSimplifyTemplate);
                redis.set(redisKey, userJson.toJSONString(), Const.REDIS_PREFIX.USER_INFO_SECONDS);
            }else{
            	//如果从mysql中查询的UserAccount为空，在redis中赋值“null”，防止缓存穿透
            	redis.set(redisKey, "null", Const.REDIS_PREFIX.USER_INFO_SECONDS);
            }
        } else {
        	if(!"null".equals(str)){
        		userBasicInfo = SimplifyObjJsonUtil.getOriginObjFromSimplifyJsonStr(str, UserBasicInfo.class, SimplifyObjJsonUtil.userAccountRestoreTemplate);
        	}
        }
        return userBasicInfo;
	}

	@Override
	public void updateByPrimaryKeySelective(UserBasicInfo record) throws Exception{
		userBasicInfoMapper.updateByPrimaryKeySelective(record);
		delUserBasicInfoFromRedis(record.getUid());
	}

	@Override
	public void delUserBasicInfoFromRedis(long uid) throws Exception {
		redis.delete(Const.REDIS_PREFIX.REDIS_USER_BASIC_INFO + uid);
	}
}
