package com.dlc.modules.api.service;

import com.dlc.modules.api.entity.Agent;
import org.omg.CORBA.portable.ValueOutputStream;

import java.util.List;
import java.util.Map;

/**
 * @author chenyuexin
 * @version 1.0
 * @date 2018-07-18 19:05
 */
public interface EmployeeService {
    /**
     * 查询员工列表
     * @return
     */
    List<Map<String , Object>> listEmployee(Map<String, Object> map);
    /**
     * 保存员工
     * @param agent
     */
    void saveEmployee(Agent agent);

    /**
     * 并非真正删除，更改状态
     * @param employeeId
     */
    void deleteEmployee(Long employeeId);

    /**
     * 根据员工id查找员工信息
     * @param agentId
     * @return
     */
    Map<String, Object> findEmployee(Long agentId);
}
