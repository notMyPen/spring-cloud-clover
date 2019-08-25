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
}
