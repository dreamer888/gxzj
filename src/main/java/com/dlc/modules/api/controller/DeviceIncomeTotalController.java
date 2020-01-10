package com.dlc.modules.api.controller;

import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;
import com.dlc.modules.api.dao.CommissionDetailMapper;
import com.dlc.modules.api.service.DeviceIncomeTotalService;
import com.dlc.modules.api.vo.AgentVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***********************************
 *Class by 王楚荣
 *2018/7/18/018
 * **********************************/
@RestController
@RequestMapping("/api/deviceIncomeTotal")
public class DeviceIncomeTotalController extends BaseController{
    @Autowired
    private DeviceIncomeTotalService deviceIncomeTotalService;
    @Autowired
    private CommissionDetailMapper commissionDetailMapper;

    /**
     * 查询消耗列表
     * @param params
     * @return
     */
    @RequestMapping("/getDeviceIncomeTotalList")
    public R query(@RequestParam Map<String,Object> params){
        if (org.springframework.util.StringUtils.isEmpty(params.get("page")) || org.springframework.util.StringUtils.isEmpty(params.get("limit"))) {
            return R.error("分页信息不能为空");
        }
        if(StringUtils.isBlank((String)params.get("deviceNo"))){
            return R.reError("设备号为空");
        }
        Query query = new Query(params);
        List<Map<String, Object>> list = deviceIncomeTotalService.queryList(query);
        for(Map<String, Object> adr:list){
            Object gzhIncomeSum = adr.get("gzhIncomeSum");
            Object payPaperSum = adr.get("payPaperSum");
            Object commissionSum = adr.get("commissionSum");
            if(gzhIncomeSum!=null)
                adr.put("gzhIncomeSum",Double.valueOf(String.valueOf(gzhIncomeSum))/100.0);
            if(payPaperSum!=null)
                adr.put("payPaperSum",Double.valueOf(String.valueOf(payPaperSum))/100.0);
            if(commissionSum!=null)
                adr.put("commissionSum",Double.valueOf(String.valueOf(commissionSum))/100.0);
        }
        int total = deviceIncomeTotalService.queryCount(query);
        PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
        return R.reOk(pageUtil);
    }

    /**
     * 查询消耗统计
     * @return
     */
    @RequestMapping("/getDeviceIncomeTotal")
    public R queryTotal(@RequestParam Map<String, Object> params, HttpServletRequest req){
        AgentVo agentVo = getAgentVo(req);
        if(agentVo.getType()!=2){
            params.put("agentId",agentVo.getParentId());
        }else if(agentVo.getType()==2){
            params.put("agentId",agentVo.getId());
        }
        Object gzhIncomeSum = 0;
        Object payPaperSum = 0;
        Object commissionSum = 0;
        Object incomeTotal = 0;
        Map<String, Object> map = deviceIncomeTotalService.queryTotal(params);
        Map<String, Object> cd  = commissionDetailMapper.queryTotal(params);
        if(map!=null){
            gzhIncomeSum = map.get("gzhIncomeSum");
            incomeTotal = map.get("incomeTotal");
            payPaperSum = map.get("payPaperSum");
            if(gzhIncomeSum!=null)
                map.put("gzhIncomeSum",Double.valueOf(String.valueOf(gzhIncomeSum))/100.0);
            if(payPaperSum!=null)
                map.put("payPaperSum",Double.valueOf(String.valueOf(payPaperSum))/100.0);
            if(incomeTotal!=null)
                map.put("incomeTotal",Double.valueOf(String.valueOf(incomeTotal))/100.0);
        }else{
            map = new HashMap<>();
            map.put("gzhIncomeSum",0);
            map.put("payPaperSum",0);
            map.put("incomeTotal",0);
        }

        if(cd!=null){
            commissionSum = cd.get("commissionSum");
            if(commissionSum!=null)
                map.put("commissionSum",Double.valueOf(String.valueOf(commissionSum))/100.0);
        }else
            map.put("commissionSum",0);

            return R.reOk(map);

    }
}
