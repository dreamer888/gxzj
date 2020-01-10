package com.dlc.modules.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.dlc.common.utils.R;
import com.dlc.common.utils.StringUtil;
import com.dlc.modules.api.entity.Device;
import com.dlc.modules.api.service.DeviceService;
import com.dlc.modules.api.vo.AgentVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author chenyuexin
 * @version 1.0
 * @date 2018-07-13 16:06
 */
@RestController
@RequestMapping("/api/device")
public class DeviceController extends BaseController{
    @Autowired
    private DeviceService deviceService;
    @RequestMapping("/getDeviceList")
    public R getDeviceList(@RequestParam Map<String,Object> params, HttpServletRequest req){
        if (StringUtils.isBlank((String)params.get("id"))){
            return R.error("id不能为空");
        }
        Integer type = Integer.valueOf(String.valueOf(params.get("type")));
        if(type==2){
            AgentVo agentVo = getAgentVo(req);
            if(agentVo.getType()!=2){
                params.put("agentId",agentVo.getParentId());
            }else if(agentVo.getType()==2){
                params.put("agentId",agentVo.getId());
            }
        }
        if(type==3){
            params.put("opsId",params.get("id"));
        }
        return R.reOk(deviceService.queryList(params));
    }

    /**
     * 设备编辑接口
     * @param params
     * @param req
     * @return
     */
    @RequestMapping("/getDeviceDetail")
    public R getDeviceDetail(@RequestParam Map<String,Object> params, HttpServletRequest req){
        if (StringUtils.isBlank((String)params.get("deviceNo"))){
            return R.error("deviceNo不能为空");
        }
        return R.reOk(deviceService.queryDeviceDetail(params));
    }

    /**
     * 公众号管理下的设备列表
     * @param type
     * @param request
     * @return
     */
    @RequestMapping("/getOfficialDeviceList")
    public R getOfficialDeviceList(Integer type, HttpServletRequest request){
        Long agentId = getAgentId(request);
        List<Map<String,Object>> map = deviceService.queryOfficialDeviceList(type,agentId);
        return R.reOk(map);
    }

    /**
     * 解除绑定
     * @param
     * @param request
     * @return
     */
    @RequestMapping("/deleteDevice")
    public R deleteDevice(String deviceNo, HttpServletRequest request){
        Long agentId = getAgentId(request);
        AgentVo vo = getAgentVo(request);
        if(vo.getType()!=2){
            agentId = vo.getParentId();
        }
        deviceService.deleteR(deviceNo,agentId);
        return R.reOk();
    }

    /**
     * 设备编辑保存接口
     * @param device
     * @param opsId
     * @param opsPhone
     * @return
     */
    @RequestMapping("/eidtSave")
    public R editSave(Device device,Long opsId,String opsPhone){
        return deviceService.updateDeviceInfo(device,opsId,opsPhone);
    }

}
