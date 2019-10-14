package rrx.cnuo.service.service;

import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.service.vo.creditCenter.CreditVo;

public interface CreditService {

	@SuppressWarnings("rawtypes")
	JsonResult updateResultNotify(CreditVo vo);

}
