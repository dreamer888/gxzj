package com.dlc.modules.api.dao;

import com.dlc.modules.api.entity.AgentDeviceRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface AgentDeviceRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AgentDeviceRelation record);

    int insertSelective(AgentDeviceRelation record);

    AgentDeviceRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AgentDeviceRelation record);

    int updateByPrimaryKey(AgentDeviceRelation record);

    List<Map<String,Object>> query(Map<String,Object> params);

    List<Map<String,Object>> queryTotal(Map<String, Object> params);

    AgentDeviceRelation queryObjectByAgentIdAndDeviceNo(AgentDeviceRelation agentDeviceRelation);

    AgentDeviceRelation queryObjectBydeviceImeiAndDeviceNo(AgentDeviceRelation agentDeviceRelation);

    void deleteRelation(@Param("agentId") Long agentId, @Param("type") int type);

    void deleteRByAgentAndDevice(@Param("deviceNo") String deviceNo, @Param("agentId") Long agentId);

    List<Map<String, Object>> findAgentDeviceRelationByImei(String imei);

    void updateRelationByImei(@Param("imei") String imei, @Param("status") Integer status);

    List<Map<String, Object>> findRelationByImei(String imei);

    List<Map<String, Object>> checkOnline(Double min);

    int offline(List<String> deviceNo);

    int updateOpsByDeviceNo(Map<String, Object> paprmMap);

    void deleteRealRelation(Long agentId);
}