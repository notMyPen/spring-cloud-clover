package rrx.cnuo.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.PageVo;
import rrx.cnuo.service.service.UserOrderService;
import rrx.cnuo.service.vo.WalletInfoVo;

/**
 * 与userAccountList表相关的业务逻辑
 * @author Administrator
 *
 */
@RestController
public class UserOrderController {

	@Autowired
	private UserOrderService userOrderService;
	
	/**
	 * 获取我的钱包信息
	 * @author xuhongyu
	 * @param { 
	 * 			pageNum // 当前页数，从1开始
	 *  		pageSize // 每页数据条数
	 * 		  } 
	 * @return{
			"status": 200,
			"data": {
				amount // 钱包余额(数字)
				total // 明细科目总数量
				rowsList[{
					partnerName	 // 用户名称
					acountTypeStr // 金额变动类型
					createTime  // 交易时间
					amount 		 // 金额
				}]
			},
			"msg": "Success"
		}
	 * @throws Exception 
	 */
	@RequestMapping(value="/myWalletInfo",method=RequestMethod.POST)
	public JsonResult<WalletInfoVo> myWalletInfo(@RequestBody PageVo vo) throws Exception{
		return userOrderService.getMyWalletInfo(vo);
	}
}
