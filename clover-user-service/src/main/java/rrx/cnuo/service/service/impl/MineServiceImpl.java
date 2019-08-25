package rrx.cnuo.service.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import rrx.cnuo.cncommon.accessory.context.UserContextHolder;
import rrx.cnuo.cncommon.util.CopyProperityUtils;
import rrx.cnuo.cncommon.utils.RedisTool;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.service.accessory.config.AliOssConfigBean;
import rrx.cnuo.service.po.UserBasicInfo;
import rrx.cnuo.service.po.UserDetailInfo;
import rrx.cnuo.service.po.UserSpouseSelection;
import rrx.cnuo.service.service.MineService;
import rrx.cnuo.service.service.data.UserBasicInfoDataService;
import rrx.cnuo.service.service.data.UserDetailInfoDataService;
import rrx.cnuo.service.service.data.UserSpouseSelectionDataService;
import rrx.cnuo.service.utils.AliUtil;
import rrx.cnuo.service.vo.request.UserBasicInfoVo;
import rrx.cnuo.service.vo.request.UserDetailInfoVo;
import rrx.cnuo.service.vo.request.UserSpouseSelectionVo;

@Service
@SuppressWarnings({"unchecked","rawtypes"})
public class MineServiceImpl implements MineService {

	@Autowired private RedisTool redis;
	@Autowired private AliOssConfigBean aliOssConfigBean;
	@Autowired private UserBasicInfoDataService userBasicInfoDataService;
	@Autowired private UserDetailInfoDataService userDetailInfoDataService;
	@Autowired private UserSpouseSelectionDataService userSpouseSelectionDataService;
	
	@Override
	public JsonResult<String> uploadImg(MultipartFile file) throws Exception {
		if(file == null){
            return JsonResult.fail(JsonResult.FAIL, "参数img不能为空");
		}
		//上传图片
        String key = AliUtil.uploadPicFromMultipartFile(file, true,redis,aliOssConfigBean);
		return JsonResult.ok(key);
	}

	@Override
	public JsonResult saveBasicInfo(UserBasicInfoVo userBasicInfoVo) throws Exception{
		List<String> notNullProperty = CopyProperityUtils.getNotNullPropertyNames(userBasicInfoVo);
		if(notNullProperty.size() == 0) {
			return JsonResult.fail(JsonResult.FAIL, "请传入一项要保存的用户基本信息！");
		}
		
		UserBasicInfo record = new UserBasicInfo();
		CopyProperityUtils.copyProperiesIgnoreNull(userBasicInfoVo, record);
		Long uid = UserContextHolder.currentUser().getUserId();
		record.setUid(uid);
		userBasicInfoDataService.updateByPrimaryKeySelective(record);
		return JsonResult.ok();
	}

	@Override
	public JsonResult saveUserDetailInfoVo(UserDetailInfoVo userDetailInfoVo) throws Exception{
		List<String> notNullProperty = CopyProperityUtils.getNotNullPropertyNames(userDetailInfoVo);
		if(notNullProperty.size() == 0) {
			return JsonResult.fail(JsonResult.FAIL, "请传入一项要保存的用户详细信息！");
		}
		
		UserDetailInfo record = new UserDetailInfo();
		CopyProperityUtils.copyProperiesIgnoreNull(userDetailInfoVo, record);
		Long uid = UserContextHolder.currentUser().getUserId();
		record.setUid(uid);
		userDetailInfoDataService.updateByPrimaryKeySelective(record);
		return JsonResult.ok();
	}

	@Override
	public JsonResult saveUserSpouseSelectionVo(UserSpouseSelectionVo userSpouseSelectionVo) throws Exception {
		List<String> notNullProperty = CopyProperityUtils.getNotNullPropertyNames(userSpouseSelectionVo);
		if(notNullProperty.size() == 0) {
			return JsonResult.fail(JsonResult.FAIL, "请传入一项要保存的用户择偶条件信息！");
		}
		
		UserSpouseSelection record = new UserSpouseSelection();
		CopyProperityUtils.copyProperiesIgnoreNull(userSpouseSelectionVo, record);
		Long uid = UserContextHolder.currentUser().getUserId();
		record.setUid(uid);
		userSpouseSelectionDataService.updateByPrimaryKeySelective(record);
		return JsonResult.ok();
	}

//	@Override
//	public JsonResult<String> getShareToken() throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
