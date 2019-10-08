package rrx.cnuo.service.controller.microservices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.biz.BoardStatisVo;
import rrx.cnuo.service.service.BoardService;
import rrx.cnuo.service.service.ConstantResourcesService;
import rrx.cnuo.service.service.StatisService;
import rrx.cnuo.service.service.TestService;

@Api("biz服务给其它微服务提供服务的Controller")
@RestController
@SuppressWarnings("rawtypes")
public class BizController{

	@Autowired private TestService testService;
	@Autowired private BoardService boardService;
	@Autowired private StatisService statisService;
	@Autowired private ConstantResourcesService constantResourcesService;
	
	@GetMapping("/test/insertStatisUser")
    public void testInsertStatisUser() {
    	testService.insertStatisUser();
    }
	
	@GetMapping("/boardStatis")
	@ApiOperation(value = "根据uid获取用户桃花牌相关统计信息")
	public JsonResult<BoardStatisVo> getBoardStatis(@RequestParam @ApiParam(value = "用户uid", required = true) Long uid) throws Exception{
		return statisService.getBoardStatisVo(uid);
	}
	
	@GetMapping("/awardCardCnt")
	@ApiOperation(value = "获取用户被奖励的总的礼券数")
	public Integer getAwardCardCnt(@RequestParam @ApiParam(value = "用户uid", required = true) Long uid) throws Exception{
		return boardService.getAwardCardCnt(uid);
	}
	
	@GetMapping("/cardUserCnt")
	@ApiOperation(value = "获取用户已经用过的礼券数")
	public Integer getCardUserCnt(@RequestParam @ApiParam(value = "用户uid", required = true) Long uid) throws Exception{
		return boardService.getCardUserCnt(uid);
	}
	
	@GetMapping("/provinceNameList")
	@ApiOperation(value = "根据省id List的json字符串，获取对应的省名称List")
	public JsonResult<List<String>> getProvinceListByIdList(@RequestParam @ApiParam(value = "省idList的json字符串", required = true) String idListJsonStr){
		return constantResourcesService.getProvinceNamesByIdListStr(idListJsonStr);
	}
	
	@PutMapping("/cardAward")
	@ApiOperation(value = "奖励给当前登录用户礼券")
	public JsonResult addCardAward(@RequestParam @ApiParam("礼券奖励类型：0-购买时的奖励；1-转发奖励；2-认证奖励；3-首次审核奖励；4-生日奖励；5-连续登陆奖励; 6-管理员奖励") Byte awardType){
		return boardService.addCardAward(awardType);
	}
}
