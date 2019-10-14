package rrx.cnuo.service.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import lombok.extern.slf4j.Slf4j;
import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.accessory.context.UserContextHolder;
import rrx.cnuo.cncommon.feignclient.UserCommonFeignService;
import rrx.cnuo.cncommon.util.CopyProperityUtils;
import rrx.cnuo.cncommon.util.DateUtil;
import rrx.cnuo.cncommon.utils.RedisTool;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.feign.UserPassportVo;
import rrx.cnuo.service.accessory.consts.BoardConst;
import rrx.cnuo.service.dao.BoardLikeMapper;
import rrx.cnuo.service.dao.BoardTurnMapper;
import rrx.cnuo.service.dao.BoardViewMapper;
import rrx.cnuo.service.dao.CardAwardRecordMapper;
import rrx.cnuo.service.po.BoardLike;
import rrx.cnuo.service.po.BoardTurn;
import rrx.cnuo.service.po.BoardView;
import rrx.cnuo.service.po.CardAwardRecord;
import rrx.cnuo.service.service.BoardService;
import rrx.cnuo.service.service.StatisService;
import rrx.cnuo.service.vo.response.AwardVo;
import rrx.cnuo.service.vo.response.CanTurnVo;
import rrx.cnuo.service.vo.response.TipInfoVo;

@Slf4j
@Service
@SuppressWarnings({"rawtypes","unchecked"})
public class BoardServiceImpl implements BoardService {

	@Autowired private RedisTool redis;
	@Autowired private ThreadPoolTaskExecutor globalTaskExecutor;
	@Autowired private StatisService statisService;
	@Autowired private BoardLikeMapper boardLikeMapper;
	@Autowired private BoardViewMapper boardViewMapper;
	@Autowired private BoardTurnMapper boardTurnMapper;
	@Autowired private CardAwardRecordMapper cardAwardRecordMapper;
	@Autowired private UserCommonFeignService userCommonFeignService;
	
	@Override
	public JsonResult<TipInfoVo> getTipInfo() {
		Long uid = UserContextHolder.currentUser().getUserId();
		
		String unReadLikemeCntKey = BoardConst.REDIS_PREFIX.UNREAD_LIKEME_CNT + uid;
		Integer unReadLikeMeNum = (Integer)redis.get(unReadLikemeCntKey);
		unReadLikeMeNum = unReadLikeMeNum == null ? 0 : unReadLikeMeNum;
		
		String unReadViewmeCntKey = BoardConst.REDIS_PREFIX.UNREAD_VIEWME_CNT + uid;
		Integer unReadViewMeNum = (Integer)redis.get(unReadViewmeCntKey);
		unReadViewMeNum = unReadViewMeNum == null ? 0 : unReadViewMeNum;
		
		String unReadTurnboardCntKey = BoardConst.REDIS_PREFIX.UNREAD_TURNBOARD_CNT + uid;
		Integer unReadTurnBoardNum = (Integer)redis.get(unReadTurnboardCntKey);
		unReadTurnBoardNum = unReadTurnBoardNum == null ? 0 : unReadTurnBoardNum;
		
		boolean hasNewTip = false;
		if(unReadLikeMeNum > 0) {
			hasNewTip = true;
		}
		if(unReadViewMeNum > 0) {
			hasNewTip = true;
		}
		if(unReadTurnBoardNum > 0) {
			hasNewTip = true;
		}
		TipInfoVo tipInfoVo = new TipInfoVo();
		tipInfoVo.setHasNewTip(hasNewTip);
		tipInfoVo.setUnReadLikeMeNum(unReadLikeMeNum);
		tipInfoVo.setUnReadViewMeNum(unReadViewMeNum);
		tipInfoVo.setUnReadTurnBoardNum(unReadTurnBoardNum);
		return JsonResult.ok(tipInfoVo);
	}

	@Override
	public JsonResult updateLikeStatus(long fuid, Byte status) {
		Long uid = UserContextHolder.currentUser().getUserId();
		
		if(status != BoardConst.BixinStatus.LIKE.getCode() && status != BoardConst.BixinStatus.UNLIKE.getCode()) {
			return JsonResult.fail(JsonResult.FAIL, "参数status传递错误。");
		}
		
		BoardLike boardLikeParam = new BoardLike();
		boardLikeParam.setUid(uid);
		boardLikeParam.setFuid(fuid);
		List<BoardLike> boardLikes = boardLikeMapper.selectByParam(boardLikeParam);
		if(boardLikes != null && boardLikes.size() > 0) {
			//uid和fuid是联合唯一索引
			BoardLike boardLike = boardLikes.get(0);
			
			BoardLike record = new BoardLike();
			record.setId(boardLike.getId());
			record.setValid(status == BoardConst.BixinStatus.LIKE.getCode() ? true : false);
			boardLikeMapper.updateByPrimaryKeySelective(record);
		}else {
			if(status == BoardConst.BixinStatus.LIKE.getCode()) {
				boardLikeMapper.insertSelective(boardLikeParam);
			}else {
				return JsonResult.fail(JsonResult.FAIL, "参数status传递错误。");
			}
		}
		
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
            public void afterCommit() {
				globalTaskExecutor.execute(() -> {
					try {
						statisService.updateLikeStatusStatis(uid,fuid);
					} catch (Exception e) {
						log.error("updateLikeStatusStatis——喜欢、被喜欢统计失败", e);
					}
				});
			}
		});
		return JsonResult.ok();
	}

	@Override
	public JsonResult saveViewRecord(long fuid) {
		Long uid = UserContextHolder.currentUser().getUserId();
		
		BoardView record = new BoardView();
		record.setUid(uid);
		record.setFuid(fuid);
		boardViewMapper.insertSelective(record);
		
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				try {
					statisService.updateViewRecordStatis(uid,fuid);
				} catch (Exception e) {
					log.error("updateViewRecordStatis——浏览、被浏览统计失败", e);
				}
			}
			
		});
		return JsonResult.ok();
	}

	@Override
	public JsonResult<List<AwardVo>> getAwardList() throws Exception {
		Long uid = UserContextHolder.currentUser().getUserId();
		List<AwardVo> resultList = new ArrayList<AwardVo>();
		
		CardAwardRecord record = new CardAwardRecord();
		record.setUid(uid);
		List<CardAwardRecord> list = cardAwardRecordMapper.selectByParam(record);
		AwardVo awardVo = null;
		for(CardAwardRecord awardRecord : list) {
			awardVo = new AwardVo();
			awardVo.setCardNum(awardRecord.getAwardNum());
			awardVo.setObtainType(awardRecord.getAwardType());
			awardVo.setObtainTime(DateUtil.format(awardRecord.getCreateTime()));
			
			resultList.add(awardVo);
		}
		return JsonResult.ok(resultList);
	}
	
	@Override
	public JsonResult<CanTurnVo> getCanTurn(Long fuid) throws Exception {
		Long uid = UserContextHolder.currentUser().getUserId();
		
		//判断当前登录用户uid有没有翻过fuid的牌子
		BoardTurn record = new BoardTurn();
		record.setUid(uid);
		record.setFuid(fuid);
		int cnt = boardTurnMapper.countByParam(record);
		CanTurnVo canTurnVo = new CanTurnVo();
		if(cnt > 0) {
			//如果翻过牌子，直接返回微信号
			String wxAccount = userCommonFeignService.getUserWxAccount(fuid);
			canTurnVo.setWxAccount(wxAccount);
		}else {
			UserPassportVo userPassportVo = userCommonFeignService.getUserPassportByUid(uid);
			CopyProperityUtils.copyProperiesIgnoreNull(userPassportVo, canTurnVo);
		}
		return JsonResult.ok(canTurnVo);
	}

	@Override
	public JsonResult<String> updateTurn(Long fuid, String message,Byte price) throws Exception{
		Long uid = UserContextHolder.currentUser().getUserId();
		UserPassportVo curUserPassportVo = userCommonFeignService.getUserPassportByUid(uid);
		if(curUserPassportVo.getCardNum() < price) {
			//如果当前登录用户的所剩礼券次数 不够 翻fuid牌子
			return JsonResult.fail(JsonResult.FAIL, "对不起，您的礼券不足，请前往充值页购买礼券");
		}
		
		BoardTurn record = new BoardTurn();
		record.setUid(uid);
		record.setFuid(fuid);
		record.setUseCardNum(price);
		record.setMessage(message);
		boardTurnMapper.insertSelective(record);
		
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				try {
					statisService.updateTurnStatis(uid,fuid);
				} catch (Exception e) {
					log.error("updateTurnStatis——翻牌子后的统计失败", e);
				}
			}
			
		});
		return JsonResult.ok();
	}

	@Override
	public JsonResult addCardAward(Byte awardType) {
		if(!Const.AwardType.isContain(awardType)) {
			log.error("=========奖励类型错误==============");
			return JsonResult.fail(JsonResult.FAIL, "奖励类型错误");
		}
		int awardNum = Const.AwardType.getAwardType(awardType).getAwardNum();
		if(awardNum > 0) {
			Long uid = UserContextHolder.currentUser().getUserId();
			CardAwardRecord record = new CardAwardRecord();
			record.setUid(uid);
			record.setAwardNum(awardNum);
			record.setAwardType(awardType);
			cardAwardRecordMapper.insertSelective(record);
		}
		return JsonResult.ok();
	}

	@Override
	public Integer getAwardCardCnt(Long uid) {
		return cardAwardRecordMapper.getAwardCardCnt(uid);
	}

	@Override
	public Integer getCardUserCnt(Long uid) {
		return boardTurnMapper.getCardUserCnt(uid);
	}
}
