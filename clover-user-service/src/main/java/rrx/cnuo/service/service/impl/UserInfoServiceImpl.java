package rrx.cnuo.service.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import rrx.cnuo.cncommon.accessory.context.UserContextHolder;
import rrx.cnuo.cncommon.util.CopyProperityUtils;
import rrx.cnuo.cncommon.util.DateUtil;
import rrx.cnuo.cncommon.utils.AliUtil;
import rrx.cnuo.cncommon.vo.DataGridResult;
import rrx.cnuo.cncommon.vo.FileType;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.biz.BoardStatisVo;
import rrx.cnuo.cncommon.vo.config.AliOssConfigBean;
import rrx.cnuo.cncommon.vo.config.BasicConfig;
import rrx.cnuo.service.accessory.consts.HandlerConst;
import rrx.cnuo.service.accessory.consts.UserConst;
import rrx.cnuo.service.dao.UserPicMapper;
import rrx.cnuo.service.feignclient.BizFeignService;
import rrx.cnuo.service.po.UserBasicInfo;
import rrx.cnuo.service.po.UserCreditStatus;
import rrx.cnuo.service.po.UserDetailInfo;
import rrx.cnuo.service.po.UserPassport;
import rrx.cnuo.service.po.UserPic;
import rrx.cnuo.service.po.UserSpouseSelection;
import rrx.cnuo.service.service.UserInfoService;
import rrx.cnuo.service.service.data.UserBasicInfoDataService;
import rrx.cnuo.service.service.data.UserCreditStatusDataService;
import rrx.cnuo.service.service.data.UserDetailInfoDataService;
import rrx.cnuo.service.service.data.UserPassportDataService;
import rrx.cnuo.service.service.data.UserSpouseSelectionDataService;
import rrx.cnuo.service.vo.request.BoardQueryParam;
import rrx.cnuo.service.vo.response.BoardBasicInfoPart1Vo;
import rrx.cnuo.service.vo.response.BoardBasicInfoPart2Vo;
import rrx.cnuo.service.vo.response.BoardDetailVo;
import rrx.cnuo.service.vo.response.LifeStyle;
import rrx.cnuo.service.vo.response.UserConcept;
import rrx.cnuo.service.vo.response.UserFamilySituation;
import rrx.cnuo.service.vo.response.UserHobby;
import rrx.cnuo.service.vo.response.UserPicVo;
import rrx.cnuo.service.vo.response.UserResume;
import rrx.cnuo.service.vo.response.UserSpouseConditionVo;

@Service
@SuppressWarnings({"rawtypes","unchecked"})
public class UserInfoServiceImpl implements UserInfoService{

	@Autowired private BasicConfig basicConfig;
	@Autowired private AliOssConfigBean aliOssConfigBean;
	@Autowired private UserPassportDataService userPassportDataService;
	@Autowired private UserBasicInfoDataService userBasicInfoDataService;
	@Autowired private UserDetailInfoDataService userDetailInfoDataService;
	@Autowired private UserSpouseSelectionDataService userSpouseSelectionDataService;
	@Autowired private UserCreditStatusDataService userCreditStatusDataService;
	@Autowired private UserPicMapper userPicMapper;
	@Autowired private BizFeignService bizFeignService;
	
	@Override
	public JsonResult<DataGridResult<BoardBasicInfoPart1Vo>> getBoardList(BoardQueryParam paramVo) throws Exception{
		JsonResult checkResult = organizeParam(paramVo);
		if (!checkResult.isOk()/* || checkResult.isLogout() */) {
			return checkResult;
		}
		//分页查询主表
		PageHelper.startPage(paramVo.getPageNum(), paramVo.getPageSize());
		List<UserBasicInfo> resultList = userBasicInfoDataService.selectByParam(paramVo);
		//先用主查询获取total
		DataGridResult<UserBasicInfo> resultListResult = new DataGridResult<>(resultList);
		long total = resultListResult.getTotal();
		
		List<BoardBasicInfoPart1Vo> realResultList = new ArrayList<BoardBasicInfoPart1Vo>();
		BoardBasicInfoPart1Vo boardBasicInfoVo = null;
		for(UserBasicInfo userBasicInfo : resultList) {
			boardBasicInfoVo = new BoardBasicInfoPart1Vo();
			CopyProperityUtils.copyProperiesIgnoreNull(userBasicInfo, boardBasicInfoVo);
			
			UserCreditStatus userCreditStatus = userCreditStatusDataService.selectByPrimaryKey(userBasicInfo.getUid());
			CopyProperityUtils.copyProperiesIgnoreNull(userCreditStatus, boardBasicInfoVo);
			
			UserPassport userPassport = userPassportDataService.selectByPrimaryKey(userBasicInfo.getUid());
			CopyProperityUtils.copyProperiesIgnoreNull(userPassport, boardBasicInfoVo);
			boardBasicInfoVo.setAvatarUrl(AliUtil.getPicPathByFileName(userPassport.getAvatarUrl(),FileType.PIC.getType(), basicConfig, aliOssConfigBean));
			
			realResultList.add(boardBasicInfoVo);
		}
		
		DataGridResult<BoardBasicInfoPart1Vo> data = new DataGridResult<>(realResultList);
        data.setTotal(total);
//        data.setRequestTag(requestTag);
//        data.setCursor(cursor);
		return JsonResult.ok(data);
	}

	/**
	 * 验证、组织参数
	 * @author xuhongyu
	 * @param paramVo
	 * @return
	 * @throws Exception
	 */
	private JsonResult organizeParam(BoardQueryParam paramVo) throws Exception{
		UserBasicInfo myUserBasicInfo = null;
		if(UserContextHolder.currentUser() != null && UserContextHolder.currentUser().getUserId() != null) {
			Long uid = UserContextHolder.currentUser().getUserId();
			myUserBasicInfo = userBasicInfoDataService.selectByPrimaryKey(uid);
		}
		
		if(myUserBasicInfo == null) {//未登录
			if(paramVo.getGender() != null && paramVo.getGender() == 0) {
				paramVo.setGender(null);
			}
			// 未登录展示优质用户
			paramVo.setHighQuality(true);
		}else {//已登录
			if(paramVo.getGender() == null) {//默认查询异性
				paramVo.setGender(UserConst.Gender.getOppositeGender(myUserBasicInfo.getGender()).getCode());
			}else {
				if(paramVo.getGender() == 0) {
					paramVo.setGender(null);
				}
			}
			/*
			 * 已登录，按照展示逻辑获取列表：
			 * 1，如果用户未指定nowProvinceId, 则 nowProvinceId 和 nowCityId为用户自己所填个人信息中的省和市
			 * 2，如果用户未指定nowProvinceId，且个人信息中也没有填省和市，默认展示全国用户
			 * 3，如果用户指定了nowProvinceId，且nowProvinceId和用户个人信息中所填省一致，则默认同1
			 * 4，如果用户指定了nowProvinceId，但是nowProvinceId和用户个人信息中所填省不一致，则nowProvinceId置为用户指定的，nowCityId=null
			 * 5，如果nowProvinceId为直辖市，则nowCityId一律当null处理，直辖市不分区，也不分市。
			 */
			/* TODO 
			 * if(paramVo.getNowProvinceId() == null) {
			 * 
			 * }
			 */
		}
		
		if(paramVo.getAgeStart() != null) {
			if(paramVo.getAgeStart() < UserConst.AGE_MIN) {
				return JsonResult.fail(JsonResult.FAIL, "最小年龄不能低于"+UserConst.AGE_MIN+"周岁");
			}
			if(paramVo.getAgeEnd() != null) {
				if(paramVo.getAgeStart() > paramVo.getAgeEnd()) {
					return JsonResult.fail(JsonResult.FAIL, "年龄范围上限不能大于范围下限！");
				}
			}
			Date birthdayBgnDate = DateUtil.getYear(new Date(), paramVo.getAgeStart().intValue() * (-1));
			paramVo.setBirthdayBgn(DateUtil.getSecond(DateUtil.getRoundDate(birthdayBgnDate)));
		}
		if(paramVo.getAgeEnd() != null) {
			if(paramVo.getAgeEnd() > UserConst.AGE_MAX) {
				return JsonResult.fail(JsonResult.FAIL, "最大年龄不能高于"+UserConst.AGE_MAX+"周岁");
			}
			Date birthdayEndDate = DateUtil.getYear(new Date(), paramVo.getAgeEnd().intValue() * (-1));
			paramVo.setBirthdayEnd(DateUtil.getSecond(DateUtil.getRoundDate(birthdayEndDate)));
		}
		return JsonResult.ok();
	}

	@Override
	public JsonResult<BoardDetailVo> getDetail(Long uid) throws Exception {
		BoardDetailVo boardDetailVo = new BoardDetailVo();
		
		UserPassport userPassport = userPassportDataService.selectByPrimaryKey(uid);
		CopyProperityUtils.copyProperiesIgnoreNull(userPassport, boardDetailVo);
		boardDetailVo.setAvatarUrl(AliUtil.getPicPathByFileName(userPassport.getAvatarUrl(),FileType.PIC.getType(), basicConfig, aliOssConfigBean));
		
		UserBasicInfo userBasicInfo = userBasicInfoDataService.selectByPrimaryKey(uid);
		CopyProperityUtils.copyProperiesIgnoreNull(userBasicInfo, boardDetailVo);
		
		UserCreditStatus userCreditStatus = userCreditStatusDataService.selectByPrimaryKey(userBasicInfo.getUid());
		CopyProperityUtils.copyProperiesIgnoreNull(userCreditStatus, boardDetailVo);
		
		UserDetailInfo userDetailInfo = userDetailInfoDataService.selectByPrimaryKey(uid);
		
		boardDetailVo.setPicList(getUserPicVoList(uid));
		JsonResult<BoardStatisVo> boardStatisVoResult = bizFeignService.getBoardStatis(uid);
		int likedUserCnt = 0;
		if(boardStatisVoResult.isOk() && boardStatisVoResult.getData() != null) {
			likedUserCnt = boardStatisVoResult.getData().getLikedCnt();
		}
		boardDetailVo.setLikedUserCnt(likedUserCnt);
		CopyProperityUtils.copyProperiesIgnoreNull(userDetailInfo, boardDetailVo);
		
		BoardBasicInfoPart2Vo userBasicInfoPart2 = new BoardBasicInfoPart2Vo();
		CopyProperityUtils.copyProperiesIgnoreNull(userBasicInfo, userBasicInfoPart2);
		boardDetailVo.setUserBasicInfo(userBasicInfoPart2);
		
		UserSpouseConditionVo spouseConditionVo = new UserSpouseConditionVo();
		UserSpouseSelection spouseCondition = userSpouseSelectionDataService.selectByPrimaryKey(uid);
		CopyProperityUtils.copyProperiesIgnoreNull(spouseCondition, spouseConditionVo);
		spouseConditionVo.setWorkProvinceList(getProvinceListByIdList(spouseCondition.getWorkProvinceIdList()));
		spouseConditionVo.setHomeProvinceList(getProvinceListByIdList(spouseCondition.getHomeProvinceIdList()));
		boardDetailVo.setSpouseCondition(spouseConditionVo);
		
		UserResume userResume = new UserResume();
		CopyProperityUtils.copyProperiesIgnoreNull(userDetailInfo, userResume);
		boardDetailVo.setUserResume(userResume);
		
		UserFamilySituation userFamilySituation = new UserFamilySituation();
		CopyProperityUtils.copyProperiesIgnoreNull(userDetailInfo, userFamilySituation);
		boardDetailVo.setUserFamilySituation(userFamilySituation);
		
		UserHobby userHobby = new UserHobby();
		CopyProperityUtils.copyProperiesIgnoreNull(userDetailInfo, userHobby);
		boardDetailVo.setUserHobby(userHobby);
		
		UserConcept userConcept = new UserConcept();
		CopyProperityUtils.copyProperiesIgnoreNull(userDetailInfo, userConcept);
		boardDetailVo.setUserConcept(userConcept);
		
		LifeStyle lifeStyle = new LifeStyle();
		CopyProperityUtils.copyProperiesIgnoreNull(userDetailInfo, lifeStyle);
		boardDetailVo.setLifeStyle(lifeStyle);
		
		return JsonResult.ok(boardDetailVo);
	}
	
	/**
	 * 根据省id List的json字符串，获取省名称List
	 * @author xuhongyu
	 * @param provinceIdListJsonStr
	 * @return
	 */
	private List<String> getProvinceListByIdList(String provinceIdListJsonStr) {
		List<String> nameList = new ArrayList<String>();
		if(StringUtils.isNotBlank(provinceIdListJsonStr)) {
			bizFeignService.getProvinceListByIdList(provinceIdListJsonStr);
		}
		return nameList;
	}
	
	/**
	 * 获取某用户有效的且审核通过的图片列表
	 * @author xuhongyu
	 * @param uid
	 * @return
	 */
	private List<UserPicVo> getUserPicVoList(Long uid) {
		List<UserPicVo> picVoList = new ArrayList<UserPicVo>();
		
		UserPic userPicParam = new UserPic();
		userPicParam.setUid(uid);
		userPicParam.setValidStatus(true);
		userPicParam.setAuditStatus(HandlerConst.AuditStatus.PASS.getCode());
		List<UserPic> userPicList = userPicMapper.selectByParam(userPicParam);
		UserPicVo picVo = null;
		for(UserPic userPic : userPicList) {
			picVo = new UserPicVo();
			picVo.setPicOrder(userPic.getPicOrder());
			picVo.setPicUrl(AliUtil.getPicPathByFileName(userPic.getPicUrl(),FileType.PIC.getType(), basicConfig, aliOssConfigBean));
			picVoList.add(picVo);
		}
		return picVoList;
	}
}
