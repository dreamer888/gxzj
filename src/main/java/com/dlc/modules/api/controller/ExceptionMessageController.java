package com.dlc.modules.api.controller;

import com.dlc.common.utils.R;
import com.dlc.modules.api.entity.ExceptionMessage;
import com.dlc.modules.api.service.ExceptionMessageService;
import com.dlc.modules.api.vo.AgentVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/***********************************
 *Class by 王楚荣
 *2018/7/19/019
 * **********************************/
@RestController
@RequestMapping("/api/exceptionMessage")
public class ExceptionMessageController extends BaseController{
    @Autowired
    private ExceptionMessageService exceptionMessageService;

    @RequestMapping("/getExceptionMessageList")
    public R getExceptionMessageList(@RequestParam Map<String,Object> params,HttpServletRequest req){
        if(StringUtils.isBlank((String)params.get("agentId"))){
            return R.reError("代理商id为空");
        }
        AgentVo agentVo = getAgentVo(req);
        if(agentVo.getType()!=2){
            params.put("agentId",agentVo.getParentId());
        }else if(agentVo.getType()==2){
            params.put("agentId",agentVo.getId());
        }
        return R.reOk(exceptionMessageService.queryList(params));
    }

    @RequestMapping("/saveExceptionMessage")
    public R save(ExceptionMessage exceptionMessage){
        exceptionMessageService.save(exceptionMessage);
        return R.reOk();
    }
}
