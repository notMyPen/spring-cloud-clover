package rrx.cnuo.service.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.utils.RedisTool;
import rrx.cnuo.cncommon.utils.StarterToolUtil;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.service.dao.MsgFormIdMapper;
import rrx.cnuo.service.po.MsgFormId;
import rrx.cnuo.service.service.MsgFormIdService;

@Service
@SuppressWarnings("rawtypes")
public class MsgFormIdServiceImpl implements MsgFormIdService {

	@Autowired
	private MsgFormIdMapper msgformIdMapper;
	
	@Autowired private RedisTool redis;
	
	@Override
    public void removeExpiredFormid() throws Exception {
        long expireTime = System.currentTimeMillis() + Const.REDIS_PREFIX.CONFIRM_EXPIRE;
        msgformIdMapper.removeExpiredFormid(expireTime);
    }
	
	@Override
    public JsonResult saveFormId(MsgFormId reqVo) throws Exception {
        JsonResult result = new JsonResult();
        result.setStatus(JsonResult.SUCCESS);
        
        if (StringUtils.isBlank(reqVo.getFormId()) || reqVo.getFormId().contains("mock")) {
        	return result;
        }
        
        int count = msgformIdMapper.countByUid(reqVo.getUid());
        if (count >= Const.USER_MAX_FORMID_CNT) {
            return result;
        }
        MsgFormId formid = new MsgFormId();
        formid.setId(StarterToolUtil.generatorLongId(redis));
        formid.setUid(reqVo.getUid());
        formid.setFormId(reqVo.getFormId());
        formid.setExpireTime(System.currentTimeMillis() + Const.REDIS_PREFIX.FORMID_EXPIRE);
        msgformIdMapper.insertSelective(formid);
        return result;
    }
}
