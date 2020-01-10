package com.dlc.modules.api.controller;

import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;
import com.dlc.modules.api.service.DeviceConsumeTotalService;
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
@RequestMapping("/api/deviceConsumeTotal")
public class DeviceConsumeTotalController extends BaseController{
    @Autowired
    private DeviceConsumeTotalService deviceConsumeTotalService;

    /**
     * 查询消耗列表
     * @param params
     * @return
     */
    @RequestMapping("/getDeviceConsumeTotalList")
    public R query(@RequestParam Map<String,Object> params){
        if (org.springframework.util.StringUtils.isEmpty(params.get("page")) || org.springframework.util.StringUtils.isEmpty(params.get("limit"))) {
            return R.error("分页信息不能为空");
        }
        if(StringUtils.isBlank((String)params.get("deviceNo"))){
            return R.reError("设备号为空");
        }
        Query query = new Query(params);
        List<Map<String, Object>> list = deviceConsumeTotalService.queryList(query);
        int total = deviceConsumeTotalService.queryCount(query);
        PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
        return R.reOk(pageUtil);
    }

    /**
     * 查询消耗统计
     * @return
     */
    @RequestMapping("/getDeviceConsumeTotal")
    public R queryTotal(@RequestParam Map<String, Object> params, HttpServletRequest req){
        AgentVo agentVo = getAgentVo(req);
        if(agentVo.getType()!=2){
            params.put("agentId",agentVo.getParentId());
        }else if(agentVo.getType()==2){
            params.put("agentId",agentVo.getId());
        }
        Map<String, Object> map = deviceConsumeTotalService.queryTotal(params);
        if(map!=null) {
            return R.reOk(deviceConsumeTotalService.queryTotal(params));
        }else{
            return R.reError("没有设备数据");
        }

    }
}
