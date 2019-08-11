package rrx.cnuo.service.service;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

import rrx.cnuo.cncommon.vo.JsonResult;

public interface UserService {

	/**
	 * 表单上传图片到七牛云
	 * @author xuhongyu
	 * @return
	 * @throws Exception
	 */
//	JsonResult<JSONObject> uploadImgQiniu(MultipartFile img) throws Exception;
	
	/**
	 * 表单上传图片到阿里oss
	 * @author xuhongyu
	 * @param img
	 * @return
	 * @throws Exception
	 */
	JsonResult<JSONObject> uploadImgAliOss(MultipartFile img) throws Exception;

}
