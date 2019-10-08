package rrx.cnuo.service.service.impl.data;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.util.SimplifyObjJsonUtil;
import rrx.cnuo.cncommon.utils.RedisTool;
import rrx.cnuo.service.dao.BoardStatisMapper;
import rrx.cnuo.service.po.BoardStatis;
import rrx.cnuo.service.service.data.BoardStatisDataService;

@Service
public class BoardStatisDataServiceImpl implements BoardStatisDataService {

	@Autowired private BoardStatisMapper boardStatisMapper;
	@Autowired private RedisTool redis;
	
	@Override
	public void insertSelective(BoardStatis record) throws Exception{
		boardStatisMapper.insertSelective(record);
	}

	@Override
	public BoardStatis selectByPrimaryKey(Long userId) throws Exception{
		String redisKey  = Const.REDIS_PREFIX.REDIS_BOARD_STATIS + userId;
		String str = redis.getString(redisKey);
		BoardStatis boardStatis = null;
        if (StringUtils.isBlank(str)) {
            boardStatis = boardStatisMapper.selectByPrimaryKey(userId);
            if (boardStatis != null) {
            	JSONObject userJson = SimplifyObjJsonUtil.getSimplifyJsonObjFromOriginObj(boardStatis, SimplifyObjJsonUtil.boardStatisSimplifyTemplate);
                redis.set(redisKey, userJson.toJSONString(), Const.REDIS_PREFIX.USER_INFO_SECONDS);
            }else{
            	//如果从mysql中查询的BoardStatis为空，在redis中赋值“null”，防止缓存穿透
            	redis.set(redisKey, "null", Const.REDIS_PREFIX.USER_INFO_SECONDS);
            }
        } else {
        	if(!"null".equals(str)){
        		boardStatis = SimplifyObjJsonUtil.getOriginObjFromSimplifyJsonStr(str, BoardStatis.class, SimplifyObjJsonUtil.boardStatisRestoreTemplate);
        	}
        }
        return boardStatis;
	}

	@Override
	public void updateByPrimaryKeySelective(BoardStatis record) throws Exception{
		boardStatisMapper.updateByPrimaryKeySelective(record);
		delBoardStatisFromRedis(record.getUid());
	}

	@Override
	public void delBoardStatisFromRedis(long uid) throws Exception {
		redis.delete(Const.REDIS_PREFIX.REDIS_BOARD_STATIS + uid);
	}

}
