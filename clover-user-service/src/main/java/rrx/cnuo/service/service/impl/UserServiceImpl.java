package rrx.cnuo.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import rrx.cnuo.cncommon.utils.RedisTool;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.service.accessory.config.AliOssConfigBean;
import rrx.cnuo.service.service.UserService;
import rrx.cnuo.service.utils.AliUtil;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired private RedisTool redis;
	@Autowired private AliOssConfigBean aliOssConfigBean;
	
	@Override
	public JsonResult<JSONObject> uploadImgAliOss(MultipartFile file) throws Exception {
		JsonResult<JSONObject> result = new JsonResult<>();
		result.setStatus(JsonResult.SUCCESS);
		
		if(file == null){
			result.setStatus(JsonResult.FAIL);
            result.setMsg("参数img不能为空");
            return result;
		}
		//上传图片
        String key = AliUtil.uploadPicFromBytes(file, true,redis,aliOssConfigBean);
        log.info("/uploadFile, key:" + key);
        
        JSONObject data = new JSONObject();
        data.put("key", key);
		result.setData(data);
		result.setMsg("图片上传成功");
		return result;
	}
	
	/*@Override
	public JsonResult<JSONObject> uploadImgQiniu(MultipartFile file) throws Exception {
		JsonResult<JSONObject> result = new JsonResult<>();
		result.setStatus(JsonResult.SUCCESS);
		
		if(file == null){
			result.setStatus(JsonResult.FAIL);
            result.setMsg("参数img不能为空");
            return result;
		}
		
		//上传图片
        String imgStr = Base64Convert.toBase64((CommonsMultipartFile) file);
        String key = ToolUtil.uploadAppPicture(imgStr, redis);
        log.info("/uploadFile, key:" + key);
        
        JSONObject data = new JSONObject();
        data.put("key", key);
		result.setData(data);
		result.setMsg("图片上传成功");
		return result;
	}*/
	
}
