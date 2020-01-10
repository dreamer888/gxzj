package com.dlc.modules.api.dao;

import com.dlc.modules.api.entity.CommissionDetail;

import java.util.Map;

public interface CommissionDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CommissionDetail record);

    int insertSelective(CommissionDetail record);

    CommissionDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CommissionDetail record);

    int updateByPrimaryKey(CommissionDetail record);

    CommissionDetail findCommissionDetailByAgentId(Long agentId);

    Map<String,Object> queryTotal(Map<String, Object> params);
}