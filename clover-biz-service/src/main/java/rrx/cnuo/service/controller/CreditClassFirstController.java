package rrx.cnuo.service.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.service.service.CreditClassFirstService;

@Api("一级认证相关操作")
@RestController
@RequestMapping("/credit")
public class CreditClassFirstController {

	@Autowired private CreditClassFirstService creditClassFirstService;
	
	@ApiOperation("人脸识别第一步：获取四位随机数字")
    @GetMapping("/face/checkNum")
    public JsonResult<String> getNum(@RequestParam String idCardNo,@RequestParam String userName) throws Exception {
        return creditClassFirstService.getCheckNum(idCardNo,userName);
    }
	
	@ApiOperation("人脸识别第二步：上传视频文件到OSS云并将对应链接传给信用中心")
    @PostMapping("/face/uploadVideo")
    public JsonResult<String> videoToOss(HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
        return creditClassFirstService.uploadVideo(multiRequest);
    }
	
	@ApiOperation("人脸识别第三步：获取信用中心人脸视频校验结果")
    @GetMapping("/face/faceCheckResult")
    public JsonResult<String> getFaceCheckResut() throws Exception {
        return creditClassFirstService.updateFaceCheckResut();
    }
}
