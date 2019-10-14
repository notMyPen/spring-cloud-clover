package rrx.cnuo.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.service.service.ConstantResourcesService;
import rrx.cnuo.service.vo.response.CityVo;
import rrx.cnuo.service.vo.response.ProvinceVo;

@RestController
@RequestMapping("resource")
@Api("静态资源（学校、省、市等）操作接口")
public class ConstantResourcesController {

	@Autowired
	private ConstantResourcesService constantResourcesService;
	
	@ApiOperation("获取所有省份")
	@GetMapping(value = "/province/list")
    public JsonResult<List<ProvinceVo>> getProvinceList() {
		return constantResourcesService.getProvinceList();
    }
	
	@ApiOperation("根据省或直辖市id获取该省或直辖市下的所有地级市或区")
	@GetMapping(value = "/{provinceId}/cities")
	public JsonResult<List<CityVo>> getCityListByProvinceId(@PathVariable @ApiParam(value = "省或直辖市id",required = true,type = "Integer") Integer provinceId) {
		return constantResourcesService.getCityListByProvinceId(provinceId);
	}
}
