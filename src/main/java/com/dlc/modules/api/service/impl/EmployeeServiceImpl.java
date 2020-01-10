/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: EmployeeServiceImpl
 * Author:   Administrator
 * Date:     2018/7/18 19:05
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.dlc.modules.api.service.impl;

import com.dlc.modules.api.dao.AgentMapper;
import com.dlc.modules.api.entity.Agent;
import com.dlc.modules.api.service.AgentDeviceRelationService;
import com.dlc.modules.api.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author chenyuexin
 * @date 2018-07-18 19:05
 * @version 1.0
 */
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService{
    private final static Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private AgentDeviceRelationService relationService;

    @Override
    public List<Map<String, Object>> listEmployee(Map<String, Object> map) {
        return this.agentMapper.listEmployee(map);
    }

    @Override
    public void saveEmployee(Agent agent) {
        this.agentMapper.insertSelective(agent);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        this.agentMapper.deleteAgent(employeeId);
        this.relationService.deleteRelation(employeeId, 2); // type==2 运维人员
    }

    @Override
    public Map<String, Object> findEmployee(Long agentId) {
        return agentMapper.findEmployee(agentId);
    }
}
