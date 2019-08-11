package rrx.cnuo.service.service;

import com.alibaba.fastjson.JSONObject;

/**
 * 统计表(system_statis、user_statis、system_statis_item)数据相关操作
 * @author xuhongyu
 * @date 2019年4月2日
 */
public interface StatisService {

	/**
	 * 根据uid和UserStatisName获取UserStatisValue<br>
	 * 从redis中获取，若无从mysql中获取且set到redis中
	 * @author xuhongyu
	 * @param uid 用户id
	 * @param name 统计项key
	 * @return
	 */
	long getUserStatisValueByKey(Long uid, short name) throws Exception;
	
	/**
	 * 根据业务类型和业务id和统计项key获取system_statis_item的value<br>
	 * 从redis中获取，若无从mysql中获取且set到redis中
	 * @author xuhongyu
	 * @param businessType 业务类型
	 * @param businessId 业务id
	 * @param statisItemKey 统计项key
	 * @return
	 */
	long getSystemStatisItemValueByKey(short businessType,Long businessId, short statisItemKey) throws Exception;

	/**
	 * 申请时的统计：<br>
	 * 用户发布的所有产品中申请贷款人数<br>
	 * 用户帮忙转发的所有借款标的里实际申请借款的人数<br>
	 * 产品的申请贷款人数
	 * @author xuhongyu
	 * @param prodBidId 标的申请id
	 * @param prodPublisherUid 标的发布者uid
	 * @param prodId 标的id
	 * @throws Exception
	 */
	JSONObject updateApplyStatis(Long prodBidId, Long prodPublisherUid, Long prodId) throws Exception;

}
