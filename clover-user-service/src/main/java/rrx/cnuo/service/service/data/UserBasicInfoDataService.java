package rrx.cnuo.service.service.data;

import java.util.List;

import rrx.cnuo.service.po.UserBasicInfo;
import rrx.cnuo.service.vo.request.BoardQueryParam;

public interface UserBasicInfoDataService {

	void insertSelective(UserBasicInfo record) throws Exception;

    UserBasicInfo selectByPrimaryKey(Long uid) throws Exception;

    void updateByPrimaryKeySelective(UserBasicInfo record) throws Exception;
    
    void delUserBasicInfoFromRedis(long uid) throws Exception;

	List<UserBasicInfo> selectByParam(BoardQueryParam paramVo);

	/**
	 * 判断某个身份证有没有被别的手机号(除了当前手机号)绑定过
	 * @author xuhongyu
	 * @param idCardNo 校验身份证
	 * @param notEqTelephone 当前登录用户手机号
	 * @return
	 */
	String getUserTelByIdCardNotEqTelephone(String idCardNo,String notEqTelephone);
}
