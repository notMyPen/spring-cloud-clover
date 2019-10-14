package rrx.cnuo.service.service;

import rrx.cnuo.cncommon.vo.DataGridResult;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.service.vo.request.BoardQueryParam;
import rrx.cnuo.service.vo.response.BoardBasicInfoPart1Vo;
import rrx.cnuo.service.vo.response.BoardDetailVo;

public interface UserInfoService {

	/**
	 * 获取首页用户列表
	 * @author xuhongyu
	 * @param param
	 * @return
	 */
	JsonResult<DataGridResult<BoardBasicInfoPart1Vo>> getBoardList(BoardQueryParam param) throws Exception;

	/**
	 * 用户个人详情
	 * @author xuhongyu
	 * @param userId
	 * @return
	 */
	JsonResult<BoardDetailVo> getDetail(Long userId) throws Exception;

}
