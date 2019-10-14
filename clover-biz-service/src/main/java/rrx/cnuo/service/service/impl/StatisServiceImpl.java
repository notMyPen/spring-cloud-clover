package rrx.cnuo.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rrx.cnuo.cncommon.feignclient.UserCommonFeignService;
import rrx.cnuo.cncommon.util.CopyProperityUtils;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.biz.BoardStatisVo;
import rrx.cnuo.service.dao.BoardLikeMapper;
import rrx.cnuo.service.dao.BoardTurnMapper;
import rrx.cnuo.service.dao.BoardViewMapper;
import rrx.cnuo.service.po.BoardLike;
import rrx.cnuo.service.po.BoardStatis;
import rrx.cnuo.service.po.BoardTurn;
import rrx.cnuo.service.service.StatisService;
import rrx.cnuo.service.service.data.BoardStatisDataService;

@Service
@SuppressWarnings("unchecked")
public class StatisServiceImpl implements StatisService {

	@Autowired private BoardStatisDataService boardStatisDataService;
	@Autowired private BoardLikeMapper boardLikeMapper;
	@Autowired private BoardViewMapper boardViewMapper;
	@Autowired private BoardTurnMapper boardTurnMapper;
	@Autowired private UserCommonFeignService userCommonFeignService;
	
	@Override
	public JsonResult<BoardStatisVo> getBoardStatisVo(Long uid) throws Exception {
		if(uid == null) {
			return JsonResult.fail(JsonResult.FAIL, "参数uid不能为空");
		}
		BoardStatisVo boardStatisVo = new BoardStatisVo();
		
		BoardStatis boardStatis = boardStatisDataService.selectByPrimaryKey(uid);
		CopyProperityUtils.copyProperiesIgnoreNull(boardStatis, boardStatisVo);
		return JsonResult.ok(boardStatisVo);
	}

	@Override
	public void updateLikeStatusStatis(Long uid, long fuid) throws Exception{
		
		//uid喜欢了多少人
		BoardLike param = new BoardLike();
		param.setUid(uid);
		param.setValid(true);
		int likeCnt = boardLikeMapper.countByParam(param);
		BoardStatis boardStatis = new BoardStatis();
		boardStatis.setUid(uid);
		boardStatis.setLikeCnt(likeCnt);
		boardStatisDataService.updateByPrimaryKeySelective(boardStatis);
		
		//fuid被多少人喜欢
		param = new BoardLike();
		param.setFuid(fuid);
		param.setValid(true);
		int likedCnt = boardLikeMapper.countByParam(param);
		boardStatis = new BoardStatis();
		boardStatis.setUid(fuid);
		boardStatis.setLikedCnt(likedCnt);
		boardStatisDataService.updateByPrimaryKeySelective(boardStatis);
	}

	@Override
	public void updateViewRecordStatis(Long uid, long fuid) throws Exception {
		
		//uid浏览过多少人
		int viewCnt = boardViewMapper.getViewUserCnt(uid);
		BoardStatis boardStatis = new BoardStatis();
		boardStatis.setUid(uid);
		boardStatis.setViewCnt(viewCnt);
		boardStatisDataService.updateByPrimaryKeySelective(boardStatis);
		
		//fuid被多少人浏览过
		int viewedCnt = boardViewMapper.getViewedUserCnt(fuid);
		boardStatis = new BoardStatis();
		boardStatis.setUid(fuid);
		boardStatis.setViewedCnt(viewedCnt);
		boardStatisDataService.updateByPrimaryKeySelective(boardStatis);
	}

	@Override
	public void updateTurnStatis(Long uid, Long fuid) throws Exception {
		//uid翻过多少人牌子
		BoardTurn record = new BoardTurn();
		record.setUid(uid);
		int turnCnt = boardTurnMapper.countByParam(record);
		BoardStatis boardStatis = new BoardStatis();
		boardStatis.setUid(uid);
		boardStatis.setTurnCnt(turnCnt);
		boardStatisDataService.updateByPrimaryKeySelective(boardStatis);
		
		//fuid被多少人翻过牌子
		record = new BoardTurn();
		record.setFuid(fuid);
		int turnedCnt = boardTurnMapper.countByParam(record);
		boardStatis = new BoardStatis();
		boardStatis.setUid(fuid);
		boardStatis.setTurnedCnt(turnedCnt);
		boardStatisDataService.updateByPrimaryKeySelective(boardStatis);
		
		userCommonFeignService.updateUserCardNum(uid);
	}
	
}
