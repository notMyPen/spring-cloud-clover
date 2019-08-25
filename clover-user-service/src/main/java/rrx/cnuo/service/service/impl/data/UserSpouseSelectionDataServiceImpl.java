package rrx.cnuo.service.service.impl.data;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.util.SimplifyObjJsonUtil;
import rrx.cnuo.cncommon.utils.RedisTool;
import rrx.cnuo.service.dao.UserSpouseSelectionMapper;
import rrx.cnuo.service.po.UserSpouseSelection;
import rrx.cnuo.service.service.data.UserSpouseSelectionDataService;

@Service
public class UserSpouseSelectionDataServiceImpl implements UserSpouseSelectionDataService {

	@Autowired private RedisTool redis;
	@Autowired private UserSpouseSelectionMapper userSpouseSelectionMapper;
	
	@Override
	public void insertSelective(UserSpouseSelection record) throws Exception{
		userSpouseSelectionMapper.insertSelective(record);
	}

	@Override
	public UserSpouseSelection selectByPrimaryKey(Long userId) throws Exception{
		String redisKey  = Const.REDIS_PREFIX.REDIS_USER_SPOUSE_SELECTION + userId;
		String str = redis.getString(redisKey);
		UserSpouseSelection userSpouseSelection = null;
        if (StringUtils.isBlank(str)) {
            userSpouseSelection = userSpouseSelectionMapper.selectByPrimaryKey(userId);
            if (userSpouseSelection != null) {
            	JSONObject userJson = SimplifyObjJsonUtil.getSimplifyJsonObjFromOriginObj(userSpouseSelection, SimplifyObjJsonUtil.userSpouseSelectionSimplifyTemplate);
                redis.set(redisKey, userJson.toJSONString(), Const.REDIS_PREFIX.USER_INFO_SECONDS);
            }else{
            	//如果从mysql中查询的UserAccount为空，在redis中赋值“null”，防止缓存穿透
            	redis.set(redisKey, "null", Const.REDIS_PREFIX.USER_INFO_SECONDS);
            }
        } else {
        	if(!"null".equals(str)){
        		userSpouseSelection = SimplifyObjJsonUtil.getOriginObjFromSimplifyJsonStr(str, UserSpouseSelection.class, SimplifyObjJsonUtil.userSpouseSelectionRestoreTemplate);
        	}
        }
        return userSpouseSelection;
	}

	@Override
	public void updateByPrimaryKeySelective(UserSpouseSelection record) throws Exception{
		userSpouseSelectionMapper.updateByPrimaryKeySelective(record);
		delUserSpouseSelectionFromRedis(record.getUid());
	}

	@Override
	public void delUserSpouseSelectionFromRedis(long uid) throws Exception {
		redis.delete(Const.REDIS_PREFIX.REDIS_USER_SPOUSE_SELECTION + uid);
	}
}
