package com.dlc.modules.api.controller;

import com.dlc.common.utils.R;
import com.dlc.modules.api.service.IndexTotalService;
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
 *2018/7/17/017
 * **********************************/
@RestController
@RequestMapping("/api/index")
public class IndexTotalController extends BaseController{
    @Autowired
    private IndexTotalService indexTotalService;

    /**
     * 查询主页统计
     * @return
     */
    @RequestMapping("/getIndexTotal")
    public R query(@RequestParam Map<String,Object> params, HttpServletRequest req){
/*        if(StringUtils.isBlank((String)params.get("agentId"))){
            return R.reError("代理商id为空");
        }*/
        AgentVo agentVo = getAgentVo(req);
        if(agentVo.getType()!=2){
            params.put("agentId",agentVo.getParentId());
        }else if(agentVo.getType()==2){
            params.put("agentId",agentVo.getId());
        }
        Map<String, Object> indexTotal = indexTotalService.queryIndexTotal(params);
        if(indexTotal!=null){
            Object incomeTotal = indexTotal.get("incomeTotal");
            if(incomeTotal!=null){
                indexTotal.put("incomeTotal",Double.valueOf(String.valueOf(incomeTotal))/100);
            }
        }
        return R.reOk(indexTotal);
    }
}
