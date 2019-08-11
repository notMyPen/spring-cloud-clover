package rrx.cnuo.service.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.util.RedisTool;
import rrx.cnuo.service.accessory.consts.StatisConst;
import rrx.cnuo.service.dao.StatisBusinessMapper;
import rrx.cnuo.service.dao.StatisUserMapper;
import rrx.cnuo.service.po.StatisBusiness;
import rrx.cnuo.service.po.StatisBusinessKey;
import rrx.cnuo.service.po.StatisUser;
import rrx.cnuo.service.service.StatisService;

@Service
public class StatisServiceImpl implements StatisService {

	@Autowired
	private StatisUserMapper statisUserMapper;
	
	@Autowired
	private StatisBusinessMapper statisBusinessMapper;
	
	@Autowired
    private RedisTool redis;

	@Override
	public long getUserStatisValueByKey(Long userId, short name) throws Exception{
		long userStatisValue = 0;
		String mapKey = Const.REDIS_PREFIX.REDIS_USER_STATIS + userId;
		String valueStr = (String) redis.getFromMap(mapKey, String.valueOf(name));
		boolean mapExist = true;
        if (StringUtils.isBlank(valueStr)) {
        	if(!redis.checkKeyExisted(mapKey)){
        		mapExist = false;
    		}
        	StatisUser userStatisKey = new StatisUser();
            userStatisKey.setUid(userId);
            userStatisKey.setName(name);
            StatisUser userStatis = statisUserMapper.selectByPrimaryKey(userStatisKey);
            if (userStatis != null) {
            	userStatisValue = userStatis.getValue();
            }
            redis.putToMap(mapKey, String.valueOf(name), String.valueOf(userStatisValue));
            if(!mapExist){
            	redis.expire(mapKey, Const.REDIS_PREFIX.USER_INFO_SECONDS);
            }
        } else {
        	userStatisValue = Long.parseLong(valueStr);
        }
		return userStatisValue;
	}

	@Override
	public long getSystemStatisItemValueByKey(short businessType,Long businessId, short statisItemKey) throws Exception{
		long statisValue = 0;
		String mapKey  = Const.REDIS_PREFIX.REDIS_SYSTEM_STATIS_ITEM + businessType + "-" + businessId;
		String valueStr = (String) redis.getFromMap(mapKey, String.valueOf(statisItemKey));
		boolean mapExist = true;
        if (StringUtils.isBlank(valueStr)) {
        	if(!redis.checkKeyExisted(mapKey)){
        		mapExist = false;
    		}
        	StatisBusinessKey itemKey = new StatisBusinessKey();
        	itemKey.setBusinessType(businessType);
        	itemKey.setBusinessId(businessId);
        	itemKey.setStatisItemKey(statisItemKey);
        	StatisBusiness systemStatisItem = statisBusinessMapper.selectByPrimaryKey(itemKey);
            if (systemStatisItem != null) {
            	statisValue = systemStatisItem.getValue();
            }
            redis.putToMap(mapKey, String.valueOf(statisItemKey), String.valueOf(statisValue));
            if(!mapExist){//如果是第一次创建map，设置过期时间
            	redis.expire(mapKey, Const.REDIS_PREFIX.USER_INFO_SECONDS);
            }
        } else {
        	statisValue = Long.parseLong(valueStr);
        }
		return statisValue;
	}

	@SuppressWarnings("unused")
	@Override
	public JSONObject updateApplyStatis(Long prodBidId, Long prodPublisherUid, Long prodId) throws Exception {
		JSONObject json = new JSONObject();
		
		// 1,用户发布的所有产品中申请贷款人数
		Long releaseApplyUserCnt = 0L;//prodBidMapper.countReleaseApplyUserCnt(prodPublisherUid);
		StatisUser releaseApplyUserStatis = new StatisUser();
		releaseApplyUserStatis.setUid(prodPublisherUid);
		short releaseApplyName = StatisConst.UserStatisName.RELEASE_APPLY_LOAN_USER_CNT.getCode();
		releaseApplyUserStatis.setName(releaseApplyName);
		releaseApplyUserStatis.setValue(releaseApplyUserCnt);
		statisUserMapper.insertOrUpdate(releaseApplyUserStatis);
		String releaseRedisKey  = Const.REDIS_PREFIX.REDIS_USER_STATIS + prodPublisherUid + "|" + releaseApplyName;//要清除的key
		json.put("releaseRedisKey", releaseRedisKey);
		
		//2,用户帮忙转发的所有借款标的里实际申请借款的人数
		List<String> forwardRedisKeys = new ArrayList<>();//要清除的key
		List<Long> forwarderUids = null;//prodBidSpreadMapper.getBidForwarderUids(prodBidId);//该标的申请的所有转发者
		if(forwarderUids != null && forwarderUids.size() > 0){
			long forwardApplyUserCnt = 0;
			StatisUser forwardApplyUserStatis = null;
			List<StatisUser> forwardApplyUserStatisList = new ArrayList<>();
			short forwarderApplyName = StatisConst.UserStatisName.FORWARD_APPLY_LOAN_USER_CNT.getCode();
			for(Long forwarderUid : forwarderUids){
				//每个转发者转发的所有借款标的里实际申请借款的人数
				List<Long> prodIds = null;//prodSpreadMapper.getProdIdsByCurrentUid(forwarderUid);//该转发者帮忙转发的所有标的(Prod)Id
				if(prodIds != null && prodIds.size() > 0){
					forwardApplyUserCnt = 0L;//prodBidMapper.countApplyUserCntByProdIds(prodIds);
					forwardApplyUserStatis = new StatisUser();
					forwardApplyUserStatis.setUid(forwarderUid);
					forwardApplyUserStatis.setName(forwarderApplyName);
					forwardApplyUserStatis.setValue(forwardApplyUserCnt);
					forwardApplyUserStatisList.add(forwardApplyUserStatis);
					forwardRedisKeys.add(Const.REDIS_PREFIX.REDIS_USER_STATIS + forwarderUid + "|" + forwarderApplyName);
				}
			}
			if(forwardApplyUserStatisList.size() > 0){
				statisUserMapper.insertOrUpdateBatch(forwardApplyUserStatisList);
			}
		}
		json.put("forwardRedisKeys", forwardRedisKeys);
		
		//3,产品的申请贷款人数
		Long prodApplyUserCnt = 0L;//prodBidMapper.countProdApplyUserCnt(prodId);
		StatisBusiness prodApplySystemStatis = new StatisBusiness();
		prodApplySystemStatis.setBusinessType(StatisConst.SystemStatisItemType.PROD.getCode());
		prodApplySystemStatis.setBusinessId(prodId);
		prodApplySystemStatis.setStatisItemKey(StatisConst.SystemStatisItemKey.APPLY_LOAN_USER_CNT.getCode());
		prodApplySystemStatis.setValue(prodApplyUserCnt);
		statisBusinessMapper.insertOrUpdate(prodApplySystemStatis);
		String prodRedisKey = Const.REDIS_PREFIX.REDIS_SYSTEM_STATIS_ITEM + StatisConst.SystemStatisItemType.PROD.getCode() + "-" + 
					prodId + "|" + StatisConst.SystemStatisItemKey.APPLY_LOAN_USER_CNT.getCode();//要清除的key
		json.put("prodRedisKey", prodRedisKey);
		return json;
	}
}
