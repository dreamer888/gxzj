package com.dlc.modules.api.service.impl;

import com.dlc.common.utils.R;
import com.dlc.modules.api.dao.AgentDeviceRelationMapper;
import com.dlc.modules.api.dao.DevicelogMapper;
import com.dlc.modules.api.entity.AgentDeviceRelation;
import com.dlc.modules.api.entity.Devicelog;
import com.dlc.modules.api.service.DevicelogService;

import java.util.Date;
import java.util.List;

import com.dlc.modules.api.service.GLService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/***********************************
 *Class by 王楚荣
 *2018/7/19/019
 * **********************************/
@Service
public class DevicelogServiceImpl implements DevicelogService {
    @Autowired
    private DevicelogMapper devicelogMapper;
    @Autowired
    private GLService glService;
    @Autowired
    private AgentDeviceRelationMapper agentDeviceRelationMapper;

    @Override
    public List<Map<String, Object>> queryList(Map<String, Object> params) {
        return devicelogMapper.queryList(params);
    }

    @Override
    public void save(Devicelog devicelog) {
        devicelog.setCreateTime(new Date());
        devicelogMapper.insertSelective(devicelog);
    }

    @Override
    public R unlockingCheck(AgentDeviceRelation agentDeviceRelation) {
        agentDeviceRelation = agentDeviceRelationMapper.queryObjectByAgentIdAndDeviceNo(agentDeviceRelation);
        if(agentDeviceRelation==null){
            return R.error("账号未绑定该设备");
        }
        JSONObject unlocking = glService.unlocking(agentDeviceRelation.getDeviceImei(), 1);
        String des = unlocking.get("des").toString();
        if (des.equalsIgnoreCase("success")){
            return R.reOk();
        }else {
            return R.reError("开锁失败");
        }
    }
}
