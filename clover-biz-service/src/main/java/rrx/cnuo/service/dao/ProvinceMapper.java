package rrx.cnuo.service.dao;

import java.util.List;

import rrx.cnuo.service.po.Province;
import rrx.cnuo.service.vo.response.ProvinceVo;

public interface ProvinceMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Province record);

    Province selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Province record);

	List<String> getNameListByIdList(List<String> idList);

	List<ProvinceVo> selectAll();
}