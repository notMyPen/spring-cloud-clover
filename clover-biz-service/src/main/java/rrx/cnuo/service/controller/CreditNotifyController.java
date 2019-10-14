package rrx.cnuo.service.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.service.service.CreditService;
import rrx.cnuo.service.vo.creditCenter.CreditVo;

/**
 * 信用中心通知接口
 * @author xuhongyu
 * @date 2019年9月6日
 */
public class CreditNotifyController {

	@Autowired
	private CreditService creditService;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/resultNotify", method = RequestMethod.POST)
	public JsonResult creditNotify(HttpServletRequest request, @RequestBody CreditVo vo) throws Exception {
		return creditService.updateResultNotify(vo);
	}
}
