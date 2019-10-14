package rrx.cnuo.service.service;

import java.util.List;

import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.service.vo.response.AwardVo;
import rrx.cnuo.service.vo.response.CanTurnVo;
import rrx.cnuo.service.vo.response.TipInfoVo;

/**
 * 桃花牌相关操作
 * @author xuhongyu
 * @date 2019年8月29日
 */
@SuppressWarnings("rawtypes")
public interface BoardService {

	/**
	 * 获取用户未读消息数
	 * @author xuhongyu
	 * @return
	 */
	JsonResult<TipInfoVo> getTipInfo();

	/**
	 * 喜欢或不喜欢
	 * @author xuhongyu
	 * @param fuid 被喜欢或被不喜欢的人
	 * @param status
	 * @return
	 */
	JsonResult updateLikeStatus(long fuid, Byte status);

	/**
	 * 添加浏览某人桃花牌记录
	 * @author xuhongyu
	 * @param fuid 被浏览的人
	 * @return
	 */
	JsonResult saveViewRecord(long fuid);

	/**
	 * 礼券获取记录
	 * @author xuhongyu
	 * @return
	 */
	JsonResult<List<AwardVo>> getAwardList() throws Exception;

	/**
	 * 获取用户的翻牌子资格
	 * @author xuhongyu
	 * @param fuid 被翻牌子的用户uid
	 * @return
	 * @throws Exception
	 */
	JsonResult<CanTurnVo> getCanTurn(Long fuid) throws Exception;

	/**
	 * 翻牌子
	 * @author xuhongyu
	 * @param fuid 被翻牌子的用户uid
	 * @param message 翻牌子捎的话
	 * @param price 翻牌子的价格(券个数)
	 * @return
	 */
	JsonResult<String> updateTurn(Long fuid, String message,Byte price) throws Exception;

	/**
	 * 奖励给当前登录用户礼券
	 * @author xuhongyu
	 * @param uid
	 * @param awardType
	 * @return
	 */
	JsonResult addCardAward(Byte awardType);

	/**
	 * 获取用户被奖励的总的礼券数
	 * @author xuhongyu
	 * @param uid
	 * @return
	 */
	Integer getAwardCardCnt(Long uid);

	/**
	 * 获取用户已经用过的礼券数
	 * @author xuhongyu
	 * @param uid
	 * @return
	 */
	Integer getCardUserCnt(Long uid);
}
