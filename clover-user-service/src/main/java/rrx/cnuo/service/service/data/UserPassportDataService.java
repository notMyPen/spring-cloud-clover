package rrx.cnuo.service.service.data;

import rrx.cnuo.service.po.UserPassport;

public interface UserPassportDataService {

	/**
	 * 根据uid获取user_passport表信息<br>
	 * 从redis中获取，若无则从mysql中获取且set到redis中
	 * @author xuhongyu
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	UserPassport selectByPrimaryKey(long uid) throws Exception;
	
	/**
	 * 删除userBasic的redis缓存
	 * @author xuhongyu
	 * @param uid
	 * @throws Exception
	 */
	void delUserAccountFromRedis(long uid) throws Exception;
	
	/**
	 * 根据条件更新用户账户表内容(置为一个定值,非累加),并删除user_passport对应的redis中key
	 * @author xuhongyu
	 * @param oaram
	 */
	void updateByPrimaryKeySelective(UserPassport param) throws Exception;

	/**
	 * 根据微信公众号openId获取user_passport表信息<br>
	 * 从redis中获取，若无则从mysql中获取且set到redis中
	 * @author xuhongyu
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	UserPassport selectByOpenid(String openId) throws Exception;

	/**
	 * 根据微信小程序openId获取user_passport表信息<br>
	 * 从redis中获取，若无则从mysql中获取且set到redis中
	 * @author xuhongyu
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	UserPassport selectByMiniOpenid(String miniOpenId) throws Exception;
}
