package com.dlc.modules.api.service;

import com.dlc.common.utils.R;
import com.dlc.modules.api.entity.AgentDeviceRelation;
import com.dlc.modules.api.entity.Devicelog;

import java.util.List;

import java.util.Map;

/***********************************
 *Class by 王楚荣
 *2018/7/19/019
 * **********************************/
public interface DevicelogService {

    List<Map<String,Object>> queryList(Map<String,Object> params);

    void save(Devicelog devicelog);

    R unlockingCheck(AgentDeviceRelation agentDeviceRelation);
}
