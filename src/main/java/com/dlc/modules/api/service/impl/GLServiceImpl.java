/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: GLServiceImpl
 * Author:   Administrator
 * Date:     2018/7/13 13:52
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.dlc.modules.api.service.impl;

import com.dlc.common.constant.Constants;
import com.dlc.common.utils.ByteUtils;
import com.dlc.common.utils.DESUtils;
import com.dlc.common.utils.HttpResponseData;
import com.dlc.common.utils.SendUtils;
import com.dlc.modules.api.controller.Handler;
import com.dlc.modules.api.entity.Agent;
import com.dlc.modules.api.entity.AgentDeviceRelation;
import com.dlc.modules.api.entity.Device;
import com.dlc.modules.api.entity.ExceptionMessage;
import com.dlc.modules.api.service.*;
import com.dlc.modules.qd.utils.PhoneCodeVer;
import com.dlc.modules.qd.utils.WxPayUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author chenyuexin
 * @date 2018-07-13 13:52
 * @version 1.0
 */
@Service
@Transactional
public class GLServiceImpl implements GLService{
    private final Logger logger = LoggerFactory.getLogger(GLServiceImpl.class);
    private static ExecutorService taskPool = Executors.newCachedThreadPool();

    @Autowired
    private AgentDeviceRelationService relationService;
    @Autowired
    private ExceptionMessageService exceptionMessageService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private AgentService agentService;

    @Override
    public JSONObject getTissue(String imei, String serial, int num) {

        Long time = new Date().getTime()/1000;
        JSONObject obj = new JSONObject();
        obj.put(Constants.ACT, Constants.CBUY);
        obj.put(Constants.IMEI, imei);
        obj.put(Constants.SERIAL,serial);
        obj.put(Constants.IID,"0");
        obj.put(Constants.NUM,num);
        obj.put(Constants.TSP,time);
        logger.info("objet-->"+obj.toString());

        JSONArray arr = new JSONArray();
        arr.add(imei);
        String message = "DLC-"+ WxPayUtils.getRandomStringByLength(6)+"-"+time;
        String result = SendUtils.send1202Data(message,arr,obj.toString());
        logger.info("result-->"+result);
        return obj.fromObject(result);
    }

    @Override
    public JSONObject unlocking(String imei, int type) {
        Long time = new Date().getTime()/1000;
        JSONObject obj = new JSONObject();
        obj.put(Constants.ACT,Constants.LOCK);
        obj.put(Constants.IMEI,imei);
        obj.put(Constants.VALUE,type);
        obj.put(Constants.TSP,time);

        JSONArray arr = new JSONArray();
        arr.add(imei);
        String message = "DLC-"+ WxPayUtils.getRandomStringByLength(6)+"-"+time;
        logger.info("message-->"+message);
        String result = SendUtils.send1202Data(message,arr,obj.toString());
        logger.info("result-->"+result);
        return obj.fromObject(result);
    }

    @Override
    public JSONObject getDevSta(String imei) {

        Long time = new Date().getTime()/1000;
        JSONObject obj = new JSONObject();
        obj.put(Constants.ACT,Constants.DEV_STA);
        obj.put(Constants.IMEI, imei);
        obj.put(Constants.TSP,time);

        JSONArray arr = new JSONArray();
        arr.add(imei);
        String message = "DLC-"+ WxPayUtils.getRandomStringByLength(6)+"-"+time;
        logger.info("message-->"+message);
        String result = SendUtils.send1202Data(message,arr,obj.toString());
        logger.info("result-->"+result);
        return obj.fromObject(result);
    }

    @Override
    public String handData(byte[] reqData, String ip) {
        String str = new String(reqData);
        int i = str.indexOf(Constants.DELIMITER);
        String h;
        if (i > 0) {
            h = str.substring(0, i);
        }
        else {
            h = str;
        }
        JSONObject headJson = JSONObject.fromObject(h);
        String tag = null;
        if (!headJson.containsKey(Constants.CODE)) {
            headJson.put("result", "77");
            headJson.put("des", "无效协议");
            return headJson.toString();
        }
        tag = headJson.getString(Constants.CODE);
        HttpResponseData httpResponseData = new HttpResponseData(tag, headJson);
        logger.info("headJSON:" + httpResponseData.getHeadJson().toString());
        if (i > 0) {
            byte[] b = ByteUtils.subBytes(reqData, i + 1);
            b = DESUtils.decrypt(b, Constants.DES_KEY);
            if (b == null) {
                headJson.put("result", "99");
                headJson.put("des", "des解密失败");
                return headJson.toString();
            }
            httpResponseData.setBodyJson(JSONObject.fromObject(new String(b)));
            logger.info("bodyJSON:" + httpResponseData.getBodyJson().toString());
            String imei = httpResponseData.getBodyJson().optString("imei");
            String err = httpResponseData.getBodyJson().optString("err");
            logger.info("imei-->"+imei+":err-->"+err);
            List<Map<String, Object>> listMap = deviceService.findDeviceAddrByImei(imei);
            logger.info("handData----listMap-->"+listMap);
            //下面是获取设备信息
            Map<String, Object> map = null;
            int status;
            if(err.equals("0")){//如果err 不等于0 就表示设备有故障
                this.relationService.updateRelationByImei(imei,1);//1 在线
                status = 1;
            }else{
                this.relationService.updateRelationByImei(imei,3);//3 是故障
                //下面是获取具体异常消息
                String errInfo = getErrInfo(err);
                logger.info("errInfo--->"+errInfo);
                if(listMap.size() > 0){
                    map = listMap.get(0);
                }
                //下面是保存异常消息
                this.saveExceptionMessage(errInfo, imei, map);
                status = 3;
            }
            //下面是为了更新设备表的状态
            if(listMap.size() > 0){
                map = listMap.get(0);
                Device device =new Device();
                device.setDeviceId(Long.valueOf(map.get("deviceId").toString()));
                device.setStatus(status); //更新设备状态
                device.setOnLineTime(new Date());
                deviceService.update(device);
            }

        }else{
            if(tag.equals("1203")){
                String imei = httpResponseData.getHeadJson().optString("imei");
                if(StringUtils.isNotBlank(imei)){
                    List<Map<String, Object>> listMap = this.relationService.findRelationByImei(imei);
                    if(listMap.size() > 0){
                        Map<String, Object> map = listMap.get(0);
                        int status = Integer.valueOf(map.get("status").toString());
                        if(status != 3){//是故障也会发送1203接口 所以只有不等3才更新状态在线
                            AgentDeviceRelation temp = new AgentDeviceRelation();
                            temp.setId(Long.valueOf(map.get("id").toString()));
                            temp.setStatus(1);//更新状态为在线
                            temp.setOnLineTime(new Date());
                            this.relationService.updateRelation(temp);
                        }
                    }
                    //更新设备表的状态及更新时间
                    listMap = deviceService.findDeviceAddrByImei(imei);
                    if(listMap.size() > 0){
                        Map<String, Object> map = listMap.get(0);
                        int status = Integer.valueOf(map.get("status").toString());
                        if(status != 3){
                            Device device =new Device();
                            device.setDeviceId(Long.valueOf(map.get("deviceId").toString()));
                            device.setStatus(1); //更新为在线状态、
                            device.setOnLineTime(new Date());
                            deviceService.update(device);
                        }
                    }
                    listMap = null;
                }
            }
        }
        String act = httpResponseData.getBodyJson().optString("act");
        if(!act.equalsIgnoreCase("cbuy")){
            switch (tag) {
                case "1201":
                    taskPool.submit(new Handler(httpResponseData));
                    break;
                case "120300":
                    taskPool.submit(new Handler(httpResponseData));
                    break;
                case "1206":
                    taskPool.submit(new Handler(httpResponseData));
                    break;
                default:
                    break;
            }
        }
        return httpResponseData.toHttpMessage();
    }

    /**
     * 获取设备异常信息类型
     * @param errNo
     * @return
     */
    public String getErrInfo(String errNo){
        String errInfo = "无货物或光电传感器故障";
        if(errNo.equals("2")){
            errInfo = "电源欠压";
        }else if(errNo.equals("3")){
            errInfo = "设备不在工作时间区";
        }else if(errNo.equals("4")){
            errInfo = "无货物";
        }else if(errNo.equals("5")){
            errInfo = "电机故障";
        }
        return errInfo;
    }

    /**
     *  保存设备异常消息
     */
    public void saveExceptionMessage(String errInfo, String imei, Map<String, Object> map){
        //logger.info("saveExceptionMessage.....");
        ExceptionMessage em = this.exceptionMessageService.findEM(imei);
        logger.info("em-->"+em);
        if(null != em){
            //取到记录时间与当前时间，然后做对比
            Calendar calendar = Calendar.getInstance();
            int nowDay = calendar.get(calendar.DATE);
            calendar.setTime(em.getCreateTime());
            int oldDay = calendar.get(calendar.DATE);
            if(nowDay == oldDay){
                em.setContext(errInfo);
                em.setCreateTime(new Date());
                this.exceptionMessageService.updateExceptionMessage(em);
            }else{
                ExceptionMessage temp = new ExceptionMessage();
                temp.setContext(errInfo);
                temp.setDeviceNo(imei);
                temp.setAddressDetail(null==map?"地址未知":map.get("addressDetail").toString());
                temp.setCreateTime(new Date());
                exceptionMessageService.save(temp);
                //下面是发送短信
                this.sendPhoneMessage(imei,errInfo);
            }
        }else{
            em = new ExceptionMessage();
            em.setContext(errInfo);
            em.setDeviceNo(imei);
            em.setAddressDetail(null==map?"地址未知":map.get("addressDetail").toString());
            em.setCreateTime(new Date());
            exceptionMessageService.save(em);
            //下面是发送短信
            this.sendPhoneMessage(imei,errInfo);
        }

    }

    /**
     * 发送短信给代理人与运维人员
     * @param imei
     * @param errInfo
     */
    public void sendPhoneMessage(String imei, String errInfo){
        List<Map<String, Object>> listMap = this.relationService.findRelationByImei(imei);
        logger.info("sendPhoneMessage-->listMap-->"+listMap);
        if(listMap.size() > 0){
            Map<String, Object> map = listMap.get(0);
            long agentId = Long.valueOf(map.get("agentId").toString());//代理商
            long opsId = Long.valueOf(map.get("opsId").toString());//运维人
            Agent agent = this.agentService.selectParent(agentId);
            logger.info("sendPhoneMessage--->agent:-->"+agent);
            if(null != agent){
                PhoneCodeVer.sendErrorMsg(agent.getPhone(),errInfo);
            }
            agent = this.agentService.selectParent(opsId);
            if(null != agent){
                PhoneCodeVer.sendErrorMsg(agent.getPhone(),errInfo);
            }
        }
    }
}
