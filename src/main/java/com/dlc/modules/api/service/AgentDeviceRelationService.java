package com.dlc.modules.api.service;

import com.dlc.common.utils.R;
import com.dlc.modules.api.entity.AgentDeviceRelation;

import java.util.List;
import java.util.Map;

/***********************************
 *Class by 王楚荣
 *2018/7/17/017
 * **********************************/
public interface AgentDeviceRelationService {

    List<Map<String,Object>> queryList(Map<String,Object> params);

    R update(AgentDeviceRelation agentDeviceRelation);

    R addRelation(AgentDeviceRelation agentDeviceRelation);

    /**
     * 删除人员与设备的关系
     * @param agentId
     * @param type 人员类型
     */
    void deleteRelation(Long agentId,int type);

    void updateRelationByImei(String imei, Integer status);

    /**
     * 根据设备imei 查询 设备与代理商关系记录(连表)
     * @param imei
     * @return
     */
    List<Map<String, Object>> findAgentDeviceRelationByImei(String imei);
    /**
     * 根据设备imei 查询 设备与代理商关系记录
     * @param imei
     * @return
     */
    List<Map<String, Object>> findRelationByImei(String imei);

    /**
     * 更新
     * @param relation
     */
    void updateRelation(AgentDeviceRelation relation);

    /**
     * 真实删除代理商绑定设备关系
     * @param agentId
     */
    void deleteRealRelation(Long agentId);

}
