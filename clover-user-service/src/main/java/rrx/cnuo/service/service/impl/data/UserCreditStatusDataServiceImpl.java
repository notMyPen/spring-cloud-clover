package rrx.cnuo.service.service.impl.data;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.util.SimplifyObjJsonUtil;
import rrx.cnuo.cncommon.utils.RedisTool;
import rrx.cnuo.service.dao.UserCreditStatusMapper;
import rrx.cnuo.service.po.UserCreditStatus;
import rrx.cnuo.service.service.data.UserCreditStatusDataService;

@Service
public class UserCreditStatusDataServiceImpl implements UserCreditStatusDataService {

	@Autowired private RedisTool redis;
	@Autowired private UserCreditStatusMapper userCreditStatusMapper;
	
	@Override
	public void insertSelective(UserCreditStatus record) throws Exception{
		userCreditStatusMapper.insertSelective(record);
	}
	
	@Override
	public UserCreditStatus selectByPrimaryKey(Long userId) throws Exception{
		String redisKey  = Const.REDIS_PREFIX.REDIS_USER_CREDIT_STATUS + userId;
		String str = redis.getString(redisKey);
		UserCreditStatus userCreditStatus = null;
        if (StringUtils.isBlank(str)) {
            userCreditStatus = userCreditStatusMapper.selectByPrimaryKey(userId);
            if (userCreditStatus != null) {
            	JSONObject userJson = SimplifyObjJsonUtil.getSimplifyJsonObjFromOriginObj(userCreditStatus, SimplifyObjJsonUtil.userCreditStatusSimplifyTemplate);
                redis.set(redisKey, userJson.toJSONString(), Const.REDIS_PREFIX.USER_INFO_SECONDS);
            }else{
            	//如果从mysql中查询的UserAccount为空，在redis中赋值“null”，防止缓存穿透
            	redis.set(redisKey, "null", Const.REDIS_PREFIX.USER_INFO_SECONDS);
            }
        } else {
        	if(!"null".equals(str)){
        		userCreditStatus = SimplifyObjJsonUtil.getOriginObjFromSimplifyJsonStr(str, UserCreditStatus.class, SimplifyObjJsonUtil.userCreditStatusRestoreTemplate);
        	}
        }
        return userCreditStatus;
	}
	
	@Override
	public void updateByPrimaryKeySelective(UserCreditStatus record) throws Exception{
		userCreditStatusMapper.updateByPrimaryKeySelective(record);
		delUserCreditStatusFromRedis(record.getUid());
	}
	
	@Override
	public void delUserCreditStatusFromRedis(long uid) throws Exception {
		redis.delete(Const.REDIS_PREFIX.REDIS_USER_CREDIT_STATUS + uid);
	}
}
