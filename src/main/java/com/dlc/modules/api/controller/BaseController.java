/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: BaseController
 * Author:   Administrator
 * Date:     2018/5/23 20:11
 * Description: 基础公共类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.dlc.modules.api.controller;

import com.dlc.common.utils.ConfigConstant;
import com.dlc.modules.api.entity.Agent;
import com.dlc.modules.api.vo.AgentVo;
import com.dlc.modules.api.vo.UserInfoVo;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenyuexin
 * @date 2018-05-23 20:11
 * @version 1.0
 */
@Controller
public class BaseController {

   public Long getAgentId(HttpServletRequest req){
        AgentVo agent = (AgentVo) req.getSession().getAttribute(ConfigConstant.ACCOUNT);
        return agent.getId();
    }

    public AgentVo getAgentVo(HttpServletRequest req){
        AgentVo agent = (AgentVo) req.getSession().getAttribute(ConfigConstant.ACCOUNT);
        return agent;
    }
}
