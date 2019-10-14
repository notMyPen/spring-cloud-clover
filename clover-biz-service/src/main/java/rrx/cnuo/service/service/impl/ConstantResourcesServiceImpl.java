package rrx.cnuo.service.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.service.dao.CityMapper;
import rrx.cnuo.service.dao.ProvinceMapper;
import rrx.cnuo.service.service.ConstantResourcesService;
import rrx.cnuo.service.vo.response.CityVo;
import rrx.cnuo.service.vo.response.ProvinceVo;

@Service
public class ConstantResourcesServiceImpl implements ConstantResourcesService {

	@Autowired
	private ProvinceMapper provinceMapper;
	
	@Autowired
	private CityMapper cityMapper;
	
	@Override
	public JsonResult<List<String>> getProvinceNamesByIdListStr(String provinceIdListJsonStr) {
		List<String> nameList = new ArrayList<>();
		if(StringUtils.isNotBlank(provinceIdListJsonStr)) {
			List<String> idList = JSON.parseArray(provinceIdListJsonStr, String.class);
			if(idList != null && idList.size() > 0) {
				nameList = provinceMapper.getNameListByIdList(idList);
			}
		}
		return JsonResult.ok(nameList);
	}

	@Override
	public JsonResult<List<ProvinceVo>> getProvinceList() {
		List<ProvinceVo> allProvinces = provinceMapper.selectAll();
		return JsonResult.ok(allProvinces);
	}

	@Override
	public JsonResult<List<CityVo>> getCityListByProvinceId(Integer provinceId) {
		List<CityVo> cityList = cityMapper.getCityListByProvinceId(provinceId);
		return JsonResult.ok(cityList);
	}

}
