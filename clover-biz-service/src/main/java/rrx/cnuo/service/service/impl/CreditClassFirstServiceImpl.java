package rrx.cnuo.service.service.impl;

import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;

import rrx.cnuo.cncommon.accessory.context.UserContextHolder;
import rrx.cnuo.cncommon.util.http.HttpClient;
import rrx.cnuo.cncommon.utils.AliUtil;
import rrx.cnuo.cncommon.utils.FileCompressUtils;
import rrx.cnuo.cncommon.utils.RedisTool;
import rrx.cnuo.cncommon.vo.FileType;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.config.AliOssConfigBean;
import rrx.cnuo.cncommon.vo.config.BasicConfig;
import rrx.cnuo.service.accessory.config.CreditCenterCofingBean;
import rrx.cnuo.service.feignclient.UserFeignService;
import rrx.cnuo.service.service.CreditClassFirstService;
import rrx.cnuo.service.vo.creditCenter.RegisterParam;
import rrx.cnuo.service.vo.creditCenter.UnderlineBasicParam;
import rrx.cnuo.service.vo.creditCenter.face.CheckNumParam;
import rrx.cnuo.service.vo.creditCenter.face.SendOssKeyToCreditParam;
import rrx.cnuo.service.vo.creditCenter.face.VideoParams;

@Service
@SuppressWarnings({"unchecked","rawtypes"})
public class CreditClassFirstServiceImpl implements CreditClassFirstService {
	
	@Autowired private CreditCenterCofingBean creditCenterCofingBean;
	@Autowired private BasicConfig basicConfig;
	@Autowired private UserFeignService userFeignService;
	@Autowired private AliOssConfigBean aliOssConfigBean;
	@Autowired private RedisTool redis;
	
	@Override
	public void registCredit(Long uid, String telephone) throws Exception{
		String url  =creditCenterCofingBean.getCreditBaseUrl() + creditCenterCofingBean.getCreditUserRegister();
		RegisterParam accreditParam = new RegisterParam();
		accreditParam.setUserId(uid.toString());
		accreditParam.setSystemType(basicConfig.getSystemName());
		accreditParam.setTelephone(telephone);
		JsonResult<?> result =  HttpClient.doPostWrapResult(url,JSON.toJSONString(accreditParam));
		if(result != null && result.isOk()) {
			userFeignService.updateUserRegistCredit(uid);
		}
	}

	@Override
	public JsonResult<String> getCheckNum(String idCardNo, String userName) throws Exception{
		if(StringUtils.isBlank(idCardNo)) {
			return JsonResult.fail(JsonResult.FAIL, "身份证号不能为空！ ");
		}
		if(StringUtils.isBlank(userName)) {
			return JsonResult.fail(JsonResult.FAIL, "用户姓名不能为空！ ");
		}
		JsonResult result = userFeignService.getFaceVerifyCheck(idCardNo);
		if(!result.isOk()) {
			return result;
		}
		Long uid = UserContextHolder.currentUser().getUserId();
		
		CheckNumParam checkNumParam = new CheckNumParam();
		checkNumParam.setUser_id(uid.toString());
		checkNumParam.setSystem_type(basicConfig.getSystemName());
		checkNumParam.setIdCardNo(idCardNo);
		checkNumParam.setUserName(userName);
		String url = creditCenterCofingBean.getCreditBaseUrl() + creditCenterCofingBean.getGetCheckNum();
		return HttpClient.doPostWrapResult(url,JSON.toJSONString(checkNumParam),String.class);
	}
	
	@Override
	public JsonResult<String> uploadVideo(MultipartHttpServletRequest multiRequest) throws Exception{
		if(multiRequest == null) {
			return JsonResult.fail(JsonResult.FAIL, "参数不能为空");
		}
		Iterator<String> iter = multiRequest.getFileNames();
        String originFileName = iter.next();
        //取得上传文件
        MultipartFile file = multiRequest.getFile(originFileName);
        if(file == null) {
        	return JsonResult.fail(JsonResult.FAIL, "上传视频文件为空！");
        }
        if(!FileCompressUtils.checkVideoOutSize(file.getSize())) {
        	return JsonResult.fail(JsonResult.FAIL, "上传视频大小不能超过" + FileCompressUtils.MAX_VIDEO_SIZE + "M(兆)！");
        }
		String key = AliUtil.uploadFileFromMultipartFile(file,FileType.VIDEO.getType(), false, redis, aliOssConfigBean);
		
		Long uid = UserContextHolder.currentUser().getUserId();
		
		//传给信用中心
		String url  =creditCenterCofingBean.getCreditBaseUrl() + creditCenterCofingBean.getSendOssKeyToCredit();
		SendOssKeyToCreditParam param = new SendOssKeyToCreditParam();
		param.setUser_id(uid.toString());;
		param.setSystem_type(basicConfig.getSystemName());
		VideoParams videoParams = new VideoParams();
		videoParams.setEndpoint("http://" + aliOssConfigBean.getOssBucketUrl());
		videoParams.setBucketName(aliOssConfigBean.getOssWxappBucketName());
		videoParams.setAccessKeyId(aliOssConfigBean.getAliyunAccesskeyId());
		videoParams.setAccessKeySecret(aliOssConfigBean.getAliyunAccesskeySecret());
		videoParams.setObjectName(aliOssConfigBean.getVideoPath() + "/" + key);
		param.setVideoParams(videoParams);
		return HttpClient.doPostWrapResult(url,JSON.toJSONString(param),String.class);
	}

	@Override
	public JsonResult<String> updateFaceCheckResut() throws Exception{
		Long uid = UserContextHolder.currentUser().getUserId();
		
		String url = creditCenterCofingBean.getCreditBaseUrl() + creditCenterCofingBean.getGetFaceCheckResult();
		UnderlineBasicParam param = new UnderlineBasicParam();
		param.setUser_id(uid.toString());;
		param.setSystem_type(basicConfig.getSystemName());
		JsonResult result = HttpClient.doPostWrapResult(url,JSON.toJSONString(param));
		if(result.isOk()) {
			
		}
		return null;
	}

}
