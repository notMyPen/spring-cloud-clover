package rrx.cnuo.service.service.impl.data;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.util.SimplifyObjJsonUtil;
import rrx.cnuo.cncommon.utils.RedisTool;
import rrx.cnuo.service.dao.UserAccountMapper;
import rrx.cnuo.service.po.UserAccount;
import rrx.cnuo.service.service.data.UserAccountDataService;

@Service
public class UserAccountDataServiceImpl implements UserAccountDataService {

	@Autowired
	private UserAccountMapper userAccountMapper;
	
	@Autowired
    private RedisTool redis;
	
	@Override
	public UserAccount selectByPrimaryKey(long userId) throws Exception {
		String redisKey  = Const.REDIS_PREFIX.REDIS_USER_ACCOUNT + userId;
		String str = redis.getString(redisKey);
        UserAccount UserAccountInfo = null;
        if (StringUtils.isBlank(str)) {
            UserAccountInfo = userAccountMapper.selectByPrimaryKey(userId);
            if (UserAccountInfo != null) {
            	JSONObject userJson = SimplifyObjJsonUtil.getSimplifyJsonObjFromOriginObj(UserAccountInfo, SimplifyObjJsonUtil.userAccountSimplifyTemplate);
                redis.set(redisKey, userJson.toJSONString(), Const.REDIS_PREFIX.USER_INFO_SECONDS);
            }else{
            	//如果从mysql中查询的UserAccount为空，在redis中赋值“null”，防止缓存穿透
            	redis.set(redisKey, "null", Const.REDIS_PREFIX.USER_INFO_SECONDS);
            }
        } else {
        	if(!"null".equals(str)){
        		UserAccountInfo = SimplifyObjJsonUtil.getOriginObjFromSimplifyJsonStr(str, UserAccount.class, SimplifyObjJsonUtil.userAccountRestoreTemplate);
        	}
        }
        return UserAccountInfo;
	}

	@Override
	public void delUserAccountFromRedis(long uid) throws Exception {
		redis.delete(Const.REDIS_PREFIX.REDIS_USER_ACCOUNT + uid);
	}
	
	@Override
	public void updateUserAccountAccumulate(UserAccount userAccount) throws Exception {
		userAccountMapper.updateUserAccountAccumulate(userAccount);
		delUserAccountFromRedis(userAccount.getUid());
	}
	
	@Override
	public void updateByPrimaryKeySelective(UserAccount userAccount) throws Exception{
		userAccountMapper.updateByPrimaryKeySelective(userAccount);
		delUserAccountFromRedis(userAccount.getUid());
	}

	@Override
	public void updateUserAccountAccumulateAboutOrder(List<JSONObject> list,Byte updateType,Boolean rollBack) throws Exception{
		UserAccount account = new UserAccount();
		Integer amount = 0;
		for(JSONObject jSONObject : list){
			amount = jSONObject.getInteger("amount");
			if(amount != 0){
				if(rollBack){
					amount = amount * (-1);
				}
				account.setUid(jSONObject.getLong("uid"));
				//更新类型：0-同时更新余额和可提现余额；1-只更新余额；2-只更新可提现余额
				if(updateType == 0 || updateType == 1){
					account.setBalance(amount);
				}
				if(updateType == 0 || updateType == 2){
					account.setWithdrawBalance(amount);
				}
				updateUserAccountAccumulate(account);
			}
		}
	}

	@Override
	public void insertSelective(UserAccount userAccount) {
		userAccountMapper.insertSelective(userAccount);
	}

}
