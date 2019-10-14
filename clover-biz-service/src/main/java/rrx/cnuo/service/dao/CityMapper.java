package rrx.cnuo.service.dao;

import java.util.List;

import rrx.cnuo.service.po.City;
import rrx.cnuo.service.vo.response.CityVo;

public interface CityMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(City record);

    City selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(City record);

	List<CityVo> getCityListByProvinceId(Integer provinceId);

}