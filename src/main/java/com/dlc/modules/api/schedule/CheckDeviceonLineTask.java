package com.dlc.modules.api.schedule;

import com.dlc.modules.api.dao.AgentDeviceRelationMapper;
import com.dlc.modules.api.dao.DeviceMapper;
import com.dlc.modules.api.dao.ExceptionMessageMapper;
import com.dlc.modules.api.entity.ExceptionMessage;
import com.dlc.modules.qd.utils.PhoneCodeVer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/***********************************
 *Class by 王楚荣
 *2018/7/24/024
 * **********************************/
public class CheckDeviceonLineTask {

    @Autowired
    private AgentDeviceRelationMapper agentDeviceRelationMapper;
    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private ExceptionMessageMapper exceptionMessageMapper;

    //设置分钟
    private double min = 5*60;

    public void check(){
        //检出响应超时的在线设备
        List<Map<String, Object>> timeOutObject= deviceMapper.checkOnline(min);
        //批量更改设备为离线状态
        List<String> deviceNoList = new ArrayList<>();
        List<ExceptionMessage> exceptionMessageList = new ArrayList<>();
        for(Map<String,Object> object : timeOutObject){
            exceptionMessageList.add(new ExceptionMessage((String)object.get("deviceNo"),(String)object.get("addr"),"设备离线",new Date()));
            deviceNoList.add((String)object.get("deviceNo"));
        }
        if(deviceNoList.size()>0){
            agentDeviceRelationMapper.offline(deviceNoList);
            deviceMapper.offline(deviceNoList);
            //添加异常消息
            exceptionMessageMapper.insertAll(exceptionMessageList);
            //向代理人、维护人员发送短息
            for(Map<String,Object> object : timeOutObject){
                String msg = "设备号:"+object.get("deviceNo") + "设备,位置："+object.get("addr")+",已离线";
                if(!StringUtils.isBlank((String)object.get("agentPhone"))){
                   PhoneCodeVer.sendErrorMsg((String)object.get("agentPhone"),msg);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!StringUtils.isBlank((String)object.get("opsPone"))) {
                    PhoneCodeVer.sendErrorMsg((String) object.get("opsPone"), msg);
                }
            }
        }

    }


}
