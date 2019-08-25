package rrx.cnuo.service.service;

import org.springframework.web.multipart.MultipartFile;

import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.service.vo.request.UserBasicInfoVo;
import rrx.cnuo.service.vo.request.UserDetailInfoVo;
import rrx.cnuo.service.vo.request.UserSpouseSelectionVo;

@SuppressWarnings("rawtypes")
public interface MineService {

	/**
	 * 表单上传图片到阿里oss
	 * @author xuhongyu
	 * @param img
	 * @return
	 * @throws Exception
	 */
	JsonResult<String> uploadImg(MultipartFile img) throws Exception;

	/**
	 * 当前登录用户基本信息保存
	 * @author xuhongyu
	 * @param userBasicInfoVo
	 * @return
	 */
	JsonResult saveBasicInfo(UserBasicInfoVo userBasicInfoVo) throws Exception;

	/**
	 * 当前登录用户详细信息保存
	 * @author xuhongyu
	 * @param userDetailInfoVo
	 * @return
	 */
	JsonResult saveUserDetailInfoVo(UserDetailInfoVo userDetailInfoVo) throws Exception;

	/**
	 * 当前登录用户择偶条件保存
	 * @author xuhongyu
	 * @param userSpouseSelectionVo
	 * @return
	 */
	JsonResult saveUserSpouseSelectionVo(UserSpouseSelectionVo userSpouseSelectionVo) throws Exception;

	/**
	 * 获取当前登录用户分享token
	 * @author xuhongyu
	 * @return
	 * @throws Exception
	 */
//	JsonResult<String> getShareToken() throws Exception;
}
