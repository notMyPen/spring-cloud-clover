package rrx.cnuo.service.service.data;

import rrx.cnuo.service.po.UserSpouseSelection;

public interface UserSpouseSelectionDataService {

	void insertSelective(UserSpouseSelection record) throws Exception;

    UserSpouseSelection selectByPrimaryKey(Long uid) throws Exception;

    void delUserSpouseSelectionFromRedis(long uid) throws Exception;
    
    void updateByPrimaryKeySelective(UserSpouseSelection record) throws Exception;
}
