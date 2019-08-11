package rrx.cnuo.service.service;

import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.service.po.MsgFormId;

@SuppressWarnings("rawtypes")
public interface MsgFormIdService {

	public void removeExpiredFormid() throws Exception;
	
	public JsonResult saveFormId(MsgFormId reqVo) throws Exception;
}
