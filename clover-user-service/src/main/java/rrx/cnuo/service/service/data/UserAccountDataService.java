package rrx.cnuo.service.service.data;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

import rrx.cnuo.service.po.UserAccount;

public interface UserAccountDataService {

	/**
	 * 获取user_account表信息<br>
	 * 从redis中获取，若无则从mysql中获取且set到redis中
	 * @author xuhongyu
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	UserAccount selectByPrimaryKey(long uid) throws Exception;
	
	/**
	 * 删除userBasic的redis缓存
	 * @author xuhongyu
	 * @param uid
	 * @throws Exception
	 */
	void delUserAccountFromRedis(long uid) throws Exception;
	
	/**
	 * 以累加的方式更新账户金额,并删除user_account对应的redis中key
	 * @author xuhongyu
	 * @param useraccount
	 */
	void updateUserAccountAccumulate(UserAccount userBasic) throws Exception;
	
	/**
	 * 根据条件更新用户账户表内容(置为一个定值,非累加),并删除user_account对应的redis中key
	 * @author xuhongyu
	 * @param userAccountParam
	 */
	void updateByPrimaryKeySelective(UserAccount oaram) throws Exception;

	/**
	 * 以累加的方式，更新在某订单中某些相关用户的余额和可提现余额
	 * @author xuhongyu
	 * @param list 某订单下某些相关用户的账单统计情况
	 * @param updateType 更新类型：0-同时更新余额和可提现余额；1-只更新余额；2-只更新可提现余额
	 * @param rollBack 是否是金额变动回滚
	 */
	void updateUserAccountAccumulateAboutOrder(List<JSONObject> list,Byte updateType,Boolean rollBack) throws Exception;

	void insertSelective(UserAccount userAccount);

}
