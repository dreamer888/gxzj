/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: EmployeeController
 * Author:   Administrator
 * Date:     2018/7/18 19:02
 * Description: 员工管理action
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.dlc.modules.api.controller;

import com.dlc.common.utils.R;
import com.dlc.common.validator.ValidatorUtils;
import com.dlc.common.validator.group.AddGroup;
import com.dlc.modules.api.entity.Agent;
import com.dlc.modules.api.service.AgentService;
import com.dlc.modules.api.service.EmployeeService;
import com.dlc.modules.qd.utils.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenyuexin
 * @date 2018-07-18 19:02
 * @version 1.0
 */
@RestController
@RequestMapping("/api/employee")
public class EmployeeController extends BaseController{
    private final static Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AgentService agentService;

    /**
     * 查询员工列表
     * @param status
     * @param type
     * @param req
     * @return
     */
    @RequestMapping(value = "/listEmployee", method = RequestMethod.POST)
    public R listEmployee(String status, String type, HttpServletRequest req){
        Map<String, Object> map = new HashMap<>();
        map.put("agentId", getAgentId(req));
        if(StringUtils.isNotBlank(status)){// 按用户状态
            map.put("status", status);
        }
        if(StringUtils.isNotBlank(type)){// 按用户类型
            map.put("type", type);
        }
        List<Map<String, Object>> listMap= this.employeeService.listEmployee(map);
        return R.reOk(listMap);
    }

    @RequestMapping(value = "/saveEmployee", method = RequestMethod.POST)
    public R saveEmployee(Agent agent, String rePassword, HttpServletRequest req){
        agent.setCommissionType(0);
        agent.setCommissionValue(0);
        ValidatorUtils.validateEntity(agent, AddGroup.class);
        if(!agent.getPassword().equals(rePassword)){
            return R.reError("两次密码不一致");
        }
        Map<String, Object> map = agentService.getAgentByPhone(agent.getPhone());
        if(map != null){
            return R.reError("该用户已存在");
        }
        agent.setParentId(getAgentId(req));
        agent.setPassword(MD5Util.MD5Encode(agent.getPassword(), "utf-8"));
        agent.setWallet(0);
        agent.setRole(0);
        agent.setDeleteStatus(0);
        this.employeeService.saveEmployee(agent);
        return R.reOk();
    }

    @RequestMapping(value = "/deleteEmployee", method = RequestMethod.POST)
    public R deleteEmployee(String agentId, HttpServletRequest req){
        if(StringUtils.isBlank(agentId)){
            return R.reError("id不能为空");
        }
        this.employeeService.deleteEmployee(Long.valueOf(agentId));
        return R.reOk();
    }

    @RequestMapping(value = "/updateEmployee", method = RequestMethod.POST)
    public R updateEmployee(Agent agent, String agentId, String rePassword, HttpServletRequest req){
        if(StringUtils.isBlank(agentId)){
            return R.reError("id不能为空");
        }
        if(StringUtils.isNotBlank(agent.getPassword())){
            if(!agent.getPassword().equals(rePassword)){
                return R.reError("两次密码不一致");
            }
            agent.setPassword(MD5Util.MD5Encode(agent.getPassword(), "utf-8"));
        }else{
            agent.setPassword(null);
        }
        agent.setId(Long.valueOf(agentId));
        this.agentService.updateAgent(agent);
        return R.reOk();
    }

    @RequestMapping(value = "/findEmployee", method = RequestMethod.POST)
    public R findEmployee(String agentId, HttpServletRequest req){
        if(StringUtils.isBlank(agentId)){
            return R.reError("id不能为空");
        }
        Map<String, Object> map = this.employeeService.findEmployee(Long.valueOf(agentId));
        return R.reOk(map);
    }
}
