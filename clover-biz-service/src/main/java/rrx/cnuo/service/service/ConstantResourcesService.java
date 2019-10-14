package rrx.cnuo.service.service;

import java.util.List;

import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.service.vo.response.CityVo;
import rrx.cnuo.service.vo.response.ProvinceVo;

/**
 * 静态资源（学校、省、市等）操作
 * @author xuhongyu
 * @date 2019年8月28日
 */
public interface ConstantResourcesService {

	/**
	 * 根据省id List的json字符串，获取对应的省名称List
	 * @author xuhongyu
	 * @param provinceIdListJsonStr
	 * @return
	 */
	JsonResult<List<String>> getProvinceNamesByIdListStr(String provinceIdListJsonStr);

	JsonResult<List<ProvinceVo>> getProvinceList();

	JsonResult<List<CityVo>> getCityListByProvinceId(Integer provinceId);
}
