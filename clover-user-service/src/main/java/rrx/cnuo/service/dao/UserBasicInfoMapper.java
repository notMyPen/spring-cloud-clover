package rrx.cnuo.service.dao;

import java.util.List;

import rrx.cnuo.service.po.UserBasicInfo;
import rrx.cnuo.service.vo.request.BoardQueryParam;

public interface UserBasicInfoMapper {
    int deleteByPrimaryKey(Long uid);

    int insertSelective(UserBasicInfo record);

    UserBasicInfo selectByPrimaryKey(Long uid);

    int updateByPrimaryKeySelective(UserBasicInfo record);

	List<UserBasicInfo> selectByParam(BoardQueryParam paramVo);

	String getUserTelByIdCardNotEqTelephone(BoardQueryParam paramVo);

}