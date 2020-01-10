package com.dlc.modules.api.controller;

import com.dlc.common.utils.R;
import com.dlc.modules.api.entity.AgentDeviceRelation;
import com.dlc.modules.api.entity.Device;
import com.dlc.modules.api.entity.Devicelog;
import com.dlc.modules.api.service.DeviceService;
import com.dlc.modules.api.service.DevicelogService;
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
@RequestMapping("/api/devicelog")
public class DevicelogController extends BaseController{
    @Autowired
    private DevicelogService devicelogService;
    @Autowired
    private DeviceService deviceService;
    /**
     * 查询开锁日志
     * @param params
     * @return
     */
    @RequestMapping("/getDevicelogList")
    public R query(@RequestParam Map<String,Object> params, HttpServletRequest req){
        AgentVo agentVo = getAgentVo(req);
        if(agentVo.getType()!=2){
            params.put("agentId",agentVo.getParentId());
        }else if(agentVo.getType()==2){
            params.put("agentId",agentVo.getId());
        }
        return R.reOk(devicelogService.queryList(params));
    }

    /**
     * 保存开锁日志
     * @param devicelog
     * @return
     */
    @RequestMapping("/saveDevicelog")
    public R saveDevicelog(Devicelog devicelog,Integer inventory,Long deviceId){
        if(devicelog.getMaintainerId()==null){
            return R.reError("缺少开锁人id");
        }
        if(StringUtils.isBlank(devicelog.getDeviceNo())){
            return R.reError("设备号不能为空");
        }
        if(inventory!=null){
            Device device = new Device();
            device.setInventory(inventory);
            device.setDeviceNo(devicelog.getDeviceNo());
            device.setDeviceId(deviceId);
            deviceService.updateDeviceInfo(device,null,null);
        }
        devicelogService.save(devicelog);
        return R.reOk();
    }

    /**
     * 设备
     * @param
     * @return
     */
    @RequestMapping("/unlockingCheck")
    public R unlockingCheck(AgentDeviceRelation agentDeviceRelation){
        if(agentDeviceRelation.getAgentId()==null){
           return R.reError("缺少代理人id");
        }
        if(StringUtils.isBlank(agentDeviceRelation.getDeviceNo())){
            return R.reError("设备号不能为空");
        }
        return devicelogService.unlockingCheck(agentDeviceRelation);
    }
}
