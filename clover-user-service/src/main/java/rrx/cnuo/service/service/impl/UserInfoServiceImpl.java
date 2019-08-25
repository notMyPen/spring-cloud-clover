package rrx.cnuo.service.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.util.CopyProperityUtils;
import rrx.cnuo.cncommon.util.DateUtil;
import rrx.cnuo.cncommon.vo.DataGridResult;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.service.po.UserBasicInfo;
import rrx.cnuo.service.po.UserCreditStatus;
import rrx.cnuo.service.service.PassportService;
import rrx.cnuo.service.service.UserInfoService;
import rrx.cnuo.service.service.data.UserBasicInfoDataService;
import rrx.cnuo.service.service.data.UserCreditStatusDataService;
import rrx.cnuo.service.vo.request.BoardQueryParam;
import rrx.cnuo.service.vo.response.BoardBasicInfoVo;
import rrx.cnuo.service.vo.response.UserPassportVo;

@Service
@SuppressWarnings("unchecked")
public class UserInfoServiceImpl implements UserInfoService{

	@Autowired private PassportService passportService;
	@Autowired private UserBasicInfoDataService userBasicInfoDataService;
	@Autowired private UserCreditStatusDataService userCreditStatusDataService;
	
	@Override
	public JsonResult<DataGridResult<BoardBasicInfoVo>> getBoardList(BoardQueryParam paramVo) throws Exception{
		if(paramVo.getGender() == null || paramVo.getGender() == 0) {
			paramVo.setGender(null);
		}
		if(paramVo.getAgeStart() != null) {
			if(paramVo.getAgeStart() < Const.AGE_MIN) {
				return JsonResult.fail(JsonResult.FAIL, "最小年龄不能低于"+Const.AGE_MIN+"周岁");
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
			if(paramVo.getAgeEnd() > Const.AGE_MAX) {
				return JsonResult.fail(JsonResult.FAIL, "最大年龄不能高于"+Const.AGE_MAX+"周岁");
			}
			Date birthdayEndDate = DateUtil.getYear(new Date(), paramVo.getAgeEnd().intValue() * (-1));
			paramVo.setBirthdayEnd(DateUtil.getSecond(DateUtil.getRoundDate(birthdayEndDate)));
		}
		
		//分页查询主表
		PageHelper.startPage(paramVo.getPageNum(), paramVo.getPageSize());
		List<UserBasicInfo> resultList = userBasicInfoDataService.selectByParam(paramVo);
		//先用主查询获取total
		DataGridResult<UserBasicInfo> resultListResult = new DataGridResult<>(resultList);
		long total = resultListResult.getTotal();
		
		List<BoardBasicInfoVo> realResultList = new ArrayList<BoardBasicInfoVo>();
		BoardBasicInfoVo boardBasicInfoVo = null;
		for(UserBasicInfo userBasicInfo : resultList) {
			boardBasicInfoVo = new BoardBasicInfoVo();
			CopyProperityUtils.copyProperiesIgnoreNull(userBasicInfo, boardBasicInfoVo);
			
			UserCreditStatus userCreditStatus = userCreditStatusDataService.selectByPrimaryKey(userBasicInfo.getUid());
			CopyProperityUtils.copyProperiesIgnoreNull(userCreditStatus, boardBasicInfoVo);
			
			JsonResult<UserPassportVo> passportInfoResult = passportService.getPassportInfo(userBasicInfo.getUid());
			if(passportInfoResult.isOk()) {
				UserPassportVo passportInfo = passportInfoResult.getData();
				CopyProperityUtils.copyProperiesIgnoreNull(passportInfo, boardBasicInfoVo);
			}
			
			realResultList.add(boardBasicInfoVo);
		}
		
		DataGridResult<BoardBasicInfoVo> data = new DataGridResult<>(realResultList);
        data.setTotal(total);
		return JsonResult.ok(data);
	}

}
