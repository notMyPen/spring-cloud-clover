package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.SystemMqConsumeFail;

public interface SystemMqConsumeFailMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(SystemMqConsumeFail record);

    SystemMqConsumeFail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SystemMqConsumeFail record);

    void deleteByCorrelationId(String correlationId);
}