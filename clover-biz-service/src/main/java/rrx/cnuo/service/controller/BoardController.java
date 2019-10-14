package rrx.cnuo.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.service.service.BoardService;
import rrx.cnuo.service.vo.response.AwardVo;
import rrx.cnuo.service.vo.response.CanTurnVo;
import rrx.cnuo.service.vo.response.TipInfoVo;

@SuppressWarnings("rawtypes")
@Api("桃花牌相关操作")
@RestController("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@ApiOperation("前端定时获取用户未读消息数")
	@GetMapping(value = "/msg/hub")
    public JsonResult<TipInfoVo> getTipInfo() {
		return boardService.getTipInfo();
    }
	
	@ApiOperation("喜欢或不喜欢")
	@PostMapping(value = "/likeStatus")
	public JsonResult likeStatus(@RequestParam @ApiParam(value = "被喜欢用户uid",required = true,type = "String") String uid,
								@RequestParam @ApiParam(value = "0-不喜欢、1-喜欢",required = true,type = "Number") Byte status) {
		return boardService.updateLikeStatus(Long.parseLong(uid),status);
	}
	
	@ApiOperation("浏览某人桃花牌记录")
	@PutMapping(value = "/{uid}/viewRecord")
	public JsonResult saveViewRecord(@PathVariable @ApiParam(value = "被浏览人uid",required = true,type = "String") String uid) {
		return boardService.saveViewRecord(Long.parseLong(uid));
	}
	
	@ApiOperation("礼券获取记录")
	@GetMapping(value = "/award/list")
	public JsonResult<List<AwardVo>> getAwardList() throws Exception{
		return boardService.getAwardList();
	}
	
	@ApiOperation("获取用户的翻牌子资格")
	@GetMapping(value = "/canTurn")
	public JsonResult<CanTurnVo> getCanTurn(@RequestParam @ApiParam(value = "被翻牌子的用户uid",required = true,type = "String") String fuid) throws Exception{
		return boardService.getCanTurn(Long.parseLong(fuid));
	}
	
	@ApiOperation("翻牌子")
	@PostMapping(value = "/turn")
	public JsonResult<String> turn(@RequestParam @ApiParam(value = "被翻牌子的用户uid",required = true) String fuid,
			@RequestParam(required = false) @ApiParam(value = "翻牌子捎的话",required = false) String message,
			@RequestParam @ApiParam(value = "翻牌子的价格(券个数)",required = true) Byte price) throws Exception{
		return boardService.updateTurn(Long.parseLong(fuid),message,price);
	}
}
