package rrx.cnuo.service.service;

import rrx.cnuo.cncommon.vo.DataGridResult;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.service.vo.request.BoardQueryParam;
import rrx.cnuo.service.vo.response.BoardBasicInfoVo;

public interface UserInfoService {

	/**
	 * 获取首页用户列表
	 * @author xuhongyu
	 * @param param
	 * @return
	 */
	JsonResult<DataGridResult<BoardBasicInfoVo>> getBoardList(BoardQueryParam param) throws Exception;

}
