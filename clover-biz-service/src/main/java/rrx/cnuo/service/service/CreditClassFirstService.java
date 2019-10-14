package rrx.cnuo.service.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import rrx.cnuo.cncommon.vo.JsonResult;

/**
 * 基础认证相关操作
 * @author xuhongyu
 * @date 2019年9月6日
 */
public interface CreditClassFirstService {

	/**
	 * 注册信用中心
	 * @author xuhongyu
	 * @param uid
	 * @param telephone
	 */
	void registCredit(Long uid, String telephone) throws Exception;
	
	/**
	 * 人脸识别第一步：获取四位随机数字
	 * @author xuhongyu
	 * @param idCardNo
	 * @param userName
	 * @return
	 */
	JsonResult<String> getCheckNum(String idCardNo, String userName) throws Exception;

	/**
	 * 人脸识别第二步：上传视频文件到OSS云并将对应链接传给信用中心
	 * @author xuhongyu
	 * @param multiRequest
	 * @return
	 */
	JsonResult<String> uploadVideo(MultipartHttpServletRequest multiRequest) throws Exception;

	/**
	 * 获人脸识别第三步：取信用中心人脸视频校验结果，并将结果保存
	 * @author xuhongyu
	 * @return
	 */
	JsonResult<String> updateFaceCheckResut() throws Exception;

}
