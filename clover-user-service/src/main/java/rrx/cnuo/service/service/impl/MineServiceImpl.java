package rrx.cnuo.service.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import rrx.cnuo.cncommon.accessory.context.UserContextHolder;
import rrx.cnuo.cncommon.util.CopyProperityUtils;
import rrx.cnuo.cncommon.util.StringUtil;
import rrx.cnuo.cncommon.utils.AliUtil;
import rrx.cnuo.cncommon.utils.RedisTool;
import rrx.cnuo.cncommon.vo.FileType;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.config.AliOssConfigBean;
import rrx.cnuo.service.dao.ShareToFriendMapper;
import rrx.cnuo.service.po.UserBasicInfo;
import rrx.cnuo.service.po.UserCreditStatus;
import rrx.cnuo.service.po.UserDetailInfo;
import rrx.cnuo.service.po.UserPassport;
import rrx.cnuo.service.po.UserSpouseSelection;
import rrx.cnuo.service.service.MineService;
import rrx.cnuo.service.service.data.UserBasicInfoDataService;
import rrx.cnuo.service.service.data.UserCreditStatusDataService;
import rrx.cnuo.service.service.data.UserDetailInfoDataService;
import rrx.cnuo.service.service.data.UserPassportDataService;
import rrx.cnuo.service.service.data.UserSpouseSelectionDataService;
import rrx.cnuo.service.vo.request.UserBasicInfoVo;
import rrx.cnuo.service.vo.request.UserDetailInfoVo;
import rrx.cnuo.service.vo.request.UserSpouseSelectionVo;
import rrx.cnuo.service.vo.response.BoardTaskVo;
import rrx.cnuo.service.vo.response.UserCreditStatusVo;

@Service
@SuppressWarnings({"unchecked","rawtypes"})
public class MineServiceImpl implements MineService {

	@Autowired private RedisTool redis;
	@Autowired private AliOssConfigBean aliOssConfigBean;
	@Autowired private ShareToFriendMapper shareToFriendMapper;
	@Autowired private UserPassportDataService userPassportDataService;
	@Autowired private UserBasicInfoDataService userBasicInfoDataService;
	@Autowired private UserDetailInfoDataService userDetailInfoDataService;
	@Autowired private UserSpouseSelectionDataService userSpouseSelectionDataService;
	@Autowired private UserCreditStatusDataService userCreditStatusDataService;
	
	@Override
	public JsonResult<String> uploadImg(MultipartFile file) throws Exception {
		if(file == null){
            return JsonResult.fail(JsonResult.FAIL, "参数img不能为空");
		}
		//上传图片
        String key = AliUtil.uploadFileFromMultipartFile(file,FileType.PIC.getType(), true,redis,aliOssConfigBean);
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

	@Override
	public JsonResult<BoardTaskVo> getBoardTask() throws Exception{
		Long uid = UserContextHolder.currentUser().getUserId();
		
		UserPassport userPassport = userPassportDataService.selectByPrimaryKey(uid);
		
		BoardTaskVo boardTaskVo = new BoardTaskVo();
		boardTaskVo.setHasBindPhone(StringUtils.isNotBlank(userPassport.getRegistTel()) ? true : false);
		boardTaskVo.setHasBoard(userPassport.getBoardStatus() == 1 ? true : false);
		boardTaskVo.setIsVerified(userPassport.getCreditPass());
		int awardCnt = shareToFriendMapper.getShareToFriendAwardCnt(uid);
		boardTaskVo.setShareToFriendAward(awardCnt > 0 ? true : false);
		
		return JsonResult.ok(boardTaskVo);
	}

	@Override
	public JsonResult<UserCreditStatusVo> getCreditStatus() throws Exception {
		Long uid = UserContextHolder.currentUser().getUserId();
		UserCreditStatusVo userCreditStatusVo = new UserCreditStatusVo();
		
		UserCreditStatus userCreditStatus = userCreditStatusDataService.selectByPrimaryKey(uid);
		CopyProperityUtils.copyProperiesIgnoreNull(userCreditStatus, userCreditStatusVo);
		return JsonResult.ok(userCreditStatusVo);
	}

	@Override
	public JsonResult getFaceVerifyCheck(String idCardNo) throws Exception {
		Long uid = UserContextHolder.currentUser().getUserId();
		UserPassport userPassport = userPassportDataService.selectByPrimaryKey(uid);
		String curTelephone = userPassport.getRegistTel();
		String otherTelephone = userBasicInfoDataService.getUserTelByIdCardNotEqTelephone(idCardNo, curTelephone);
		if (StringUtils.isNotBlank(otherTelephone)) {
			return JsonResult.fail(JsonResult.FAIL, "<div class=\"telt\">该身份信息已经在平台上通过其他手机号" + StringUtil.hideTel(otherTelephone) + "注册过了。<br/>建议您：<br/>1、通过验证身份登录旧账号；<br/>2、重新输入未实名的姓名和身份证号</div>");
		}
		return JsonResult.ok();
	}

//	@Override
//	public JsonResult<String> getShareToken() throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
