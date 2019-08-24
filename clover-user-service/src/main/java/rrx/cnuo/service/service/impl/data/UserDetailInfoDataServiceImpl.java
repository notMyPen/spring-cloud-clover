package rrx.cnuo.service.service.impl.data;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.util.SimplifyObjJsonUtil;
import rrx.cnuo.cncommon.utils.RedisTool;
import rrx.cnuo.service.dao.UserDetailInfoMapper;
import rrx.cnuo.service.po.UserDetailInfo;
import rrx.cnuo.service.service.data.UserDetailInfoDataService;

@Service
public class UserDetailInfoDataServiceImpl implements UserDetailInfoDataService {

	@Autowired
    private RedisTool redis;
	
	@Autowired
	private UserDetailInfoMapper userDetailInfoMapper;
	
	@Override
	public void insertSelective(UserDetailInfo record) throws Exception{
		userDetailInfoMapper.insertSelective(record);
	}

	@Override
	public UserDetailInfo selectByPrimaryKey(Long userId) throws Exception{
		String redisKey  = Const.REDIS_PREFIX.REDIS_USER_DETAIL_INFO + userId;
		String str = redis.getString(redisKey);
		UserDetailInfo userDetailInfo = null;
        if (StringUtils.isBlank(str)) {
            userDetailInfo = userDetailInfoMapper.selectByPrimaryKey(userId);
            if (userDetailInfo != null) {
            	JSONObject userJson = SimplifyObjJsonUtil.getSimplifyJsonObjFromOriginObj(userDetailInfo, SimplifyObjJsonUtil.userDetailInfoSimplifyTemplate);
                redis.set(redisKey, userJson.toJSONString(), Const.REDIS_PREFIX.USER_INFO_SECONDS);
            }else{
            	//如果从mysql中查询的UserAccount为空，在redis中赋值“null”，防止缓存穿透
            	redis.set(redisKey, "null", Const.REDIS_PREFIX.USER_INFO_SECONDS);
            }
        } else {
        	if(!"null".equals(str)){
        		userDetailInfo = SimplifyObjJsonUtil.getOriginObjFromSimplifyJsonStr(str, UserDetailInfo.class, SimplifyObjJsonUtil.userDetailInfoRestoreTemplate);
        	}
        }
        return userDetailInfo;
	}

	@Override
	public void updateByPrimaryKeySelective(UserDetailInfo record) throws Exception {
		userDetailInfoMapper.updateByPrimaryKeySelective(record);
		delUserDetailInfoFromRedis(record.getUid());
	}

	@Override
	public void delUserDetailInfoFromRedis(long uid) throws Exception {
		redis.delete(Const.REDIS_PREFIX.REDIS_USER_DETAIL_INFO + uid);
	}

}
