package com.dlc.modules.api.controller;

import com.dlc.common.utils.R;
import com.dlc.modules.api.entity.AgentDeviceRelation;
import com.dlc.modules.api.service.AgentDeviceRelationService;
import com.dlc.modules.api.vo.AgentVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/***********************************
 *Class by 王楚荣
 *2018/7/17/017
 * **********************************/
@RestController
@RequestMapping("/api/relation")
public class AgentDeviceRelationController extends BaseController{
    @Autowired
    private AgentDeviceRelationService agentDeviceRelationService;

    @RequestMapping("/getAgentDeviceRelationList")
    public R query(@RequestParam Map<String,Object> params, HttpServletRequest req){
        if(StringUtils.isBlank((String)params.get("agentId"))){
            return R.reError("代理商id为空");
        }
        AgentVo agentVo = getAgentVo(req);
        if(agentVo.getType()!=2){
            params.put("agentId",agentVo.getParentId());
        }else if(agentVo.getType()==2){
            params.put("agentId",agentVo.getId());
        }
        List<Map<String, Object>> adrs = agentDeviceRelationService.queryList(params);
        for(Map<String, Object> adr:adrs){
            Object gzhIncomeSum = adr.get("gzhIncomeSum");
            Object payPaperSum = adr.get("payPaperSum");
            Object commissionSum = adr.get("commissionSum");
            Object incomeTotal = adr.get("incomeTotal");
            if(gzhIncomeSum!=null)
                adr.put("gzhIncomeSum",Double.valueOf(String.valueOf(gzhIncomeSum))/100.0);
            if(payPaperSum!=null)
                adr.put("payPaperSum",Double.valueOf(String.valueOf(payPaperSum))/100.0);
            if(commissionSum!=null)
                adr.put("commissionSum",Double.valueOf(String.valueOf(commissionSum))/100.0);
            if(incomeTotal!=null)
                adr.put("incomeTotal",Double.valueOf(String.valueOf(incomeTotal))/100.0);
        }
        return R.reOk(adrs);
}

    @RequestMapping("/addRelation")
    public R addRelation(AgentDeviceRelation agentDeviceRelation){
        if(StringUtils.isBlank(agentDeviceRelation.getDeviceNo())||StringUtils.isBlank(agentDeviceRelation.getDeviceImei())||agentDeviceRelation.getAgentId()==null){
            return R.reError("设备号、设备Imei、代理人Id不能为空");
        }
        return  agentDeviceRelationService.addRelation(agentDeviceRelation);
    }

    @RequestMapping("/saveFreePaperTotal")
    public R saveFreePaperTotal(Long agentId,String deviceNo,Integer freePaperTotal, HttpServletRequest req){
        AgentVo agentVo = getAgentVo(req);
        if(agentVo.getType()!=2){
            agentId = agentVo.getParentId();
        }else if(agentVo.getType()==2){
            agentId =agentVo.getId();
        }
        return agentDeviceRelationService.update(new AgentDeviceRelation(agentId,deviceNo,freePaperTotal,0,0,0,0,0));
    }

    @RequestMapping("/savePayPaperTotal")
    public R savePayPaperTotal(Long agentId,String deviceNo,Integer payPaperTotal, HttpServletRequest req){
        AgentVo agentVo = getAgentVo(req);
        if(agentVo.getType()!=2){
            agentId = agentVo.getParentId();
        }else if(agentVo.getType()==2){
            agentId =agentVo.getId();
        }
        return agentDeviceRelationService.update(new AgentDeviceRelation(agentId,deviceNo,0,payPaperTotal,0,0,0,0));
    }

    @RequestMapping("/saveGzhLikeTotal")
    public R saveGzhLikeTotal(Long agentId,String deviceNo,Integer gzhLikeTotal, HttpServletRequest req){
        AgentVo agentVo = getAgentVo(req);
        if(agentVo.getType()!=2){
            agentId = agentVo.getParentId();
        }else if(agentVo.getType()==2){
            agentId =agentVo.getId();
        }
        return agentDeviceRelationService.update(new AgentDeviceRelation(agentId,deviceNo,0,0,gzhLikeTotal,0,0,0));
    }

    @RequestMapping("/saveGzhIncomeSum")
    public R saveGzhIncomeSum(Long agentId,String deviceNo,Integer gzhIncomeSum, HttpServletRequest req){
        AgentVo agentVo = getAgentVo(req);
        if(agentVo.getType()!=2){
            agentId = agentVo.getParentId();
        }else if(agentVo.getType()==2){
            agentId =agentVo.getId();
        }
        return agentDeviceRelationService.update(new AgentDeviceRelation(agentId,deviceNo,0,0,0,gzhIncomeSum,0,0));
    }

    @RequestMapping("/savePayPaperSum")
    public R savePayPaperSum(Long agentId,String deviceNo,Integer payPaperSum, HttpServletRequest req){
        AgentVo agentVo = getAgentVo(req);
        if(agentVo.getType()!=2){
            agentId = agentVo.getParentId();
        }else if(agentVo.getType()==2){
            agentId =agentVo.getId();
        }
        return agentDeviceRelationService.update(new AgentDeviceRelation(agentId,deviceNo,0,0,0,0,payPaperSum,0));
    }

    @RequestMapping("/saveCommissionSum")
    public R saveCommissionSum(Long agentId,String deviceNo,Integer commissionSum, HttpServletRequest req){
        AgentVo agentVo = getAgentVo(req);
        if(agentVo.getType()!=2){
            agentId = agentVo.getParentId();
        }else if(agentVo.getType()==2){
            agentId =agentVo.getId();
        }
        return agentDeviceRelationService.update(new AgentDeviceRelation(agentId,deviceNo,0,0,0,0,0,commissionSum));
    }

}
