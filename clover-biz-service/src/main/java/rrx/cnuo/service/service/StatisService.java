package rrx.cnuo.service.service;

import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.biz.BoardStatisVo;

/**
 * 统计相关操作
 * @author xuhongyu
 * @date 2019年8月28日
 */
public interface StatisService {

	JsonResult<BoardStatisVo> getBoardStatisVo(Long uid) throws Exception;

	/**
	 * 喜欢、被喜欢后的统计
	 * @author xuhongyu
	 * @param uid
	 * @param fuid
	 */
	void updateLikeStatusStatis(Long uid, long fuid) throws Exception;

	/**
	 * 浏览用户吼的统计
	 * @author xuhongyu
	 * @param uid
	 * @param fuid
	 * @throws Exception
	 */
	void updateViewRecordStatis(Long uid, long fuid) throws Exception;

	/**
	 * 翻牌子后的统计
	 * @author xuhongyu
	 * @param uid
	 * @param fuid
	 * @throws Exception
	 */
	void updateTurnStatis(Long uid, Long fuid) throws Exception;

}
