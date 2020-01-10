/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: GLController
 * Author:   Administrator
 * Date:     2018/7/12 16:41
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.dlc.modules.api.controller;

import com.dlc.common.constant.Constants;
import com.dlc.common.utils.*;
import com.dlc.modules.api.service.GLService;
import com.dlc.modules.qd.utils.WxPayUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author chenyuexin
 * @date 2018-07-12 16:41
 * @version 1.0
 */
@RestController
@RequestMapping("/api/gl")
public class GLController {
    private final Logger logger = LoggerFactory.getLogger(GLController.class);
    private static ExecutorService taskPool = Executors.newCachedThreadPool();

    @Autowired
    GLService glService;

    @RequestMapping(value = "getTissue", method = RequestMethod.POST)
    public JSONObject getTissue(HttpServletRequest req,String imei, Integer num) throws Exception{
        Long time = new Date().getTime();

        JSONObject obj = this.glService.getTissue(imei,time+"",num);

        return obj;
    }

    @RequestMapping(value = "saveDevice")
    public R saveDevice(HttpServletRequest req, HttpServletResponse response) throws IOException{
        try {
            String ip = getRequestIpAddr(req);
            logger.info("saveDevice--ip--->"+ip);
            byte[] reqData = readRequest(req);
            String resultString = this.glService.handData(reqData, ip);
        } catch (IOException e) {
            logger.info("e:",e);
            response.setStatus(500);
            writeResponse(response, "ERROR".getBytes());
        }
        return R.reOk();
    }

    @RequestMapping(value = "unlocking", method = RequestMethod.POST)
    public JSONObject unlocking(HttpServletRequest req,String imei, int type){

        JSONObject obj = this.glService.unlocking(imei, type);

        return obj;
    }

    @RequestMapping(value = "getDevSta", method = RequestMethod.POST)
    public JSONObject getDevSta(HttpServletRequest req,String imei){

        JSONObject obj = this.glService.getDevSta(imei);

        return obj;
    }

    /**
     * 解析数据，有透传内容，httpResponseData中bodyJson有值，无透传内容，仅headJson有值
     * Handler业务处理逻辑需添加代码（转移到service层中）
     */
    private String handData(byte[] reqData, String ip) {
//        String str = new String(reqData);
//        int i = str.indexOf(Constants.DELIMITER);
//        String h;
//        if (i > 0) {
//            h = str.substring(0, i);
//        }
//        else {
//            h = str;
//        }
//        JSONObject headJson = JSONObject.fromObject(h);
//        String tag = null;
//        if (!headJson.containsKey(Constants.CODE)) {
//            headJson.put("result", "77");
//            headJson.put("des", "无效协议");
//            return headJson.toString();
//        }
//        tag = headJson.getString(Constants.CODE);
//        HttpResponseData httpResponseData = new HttpResponseData(tag, headJson);
//        logger.info("headJSON:" + httpResponseData.getHeadJson().toString());
//        if (i > 0) {
//            byte[] b = ByteUtils.subBytes(reqData, i + 1);
//            b = DESUtils.decrypt(b, Constants.DES_KEY);
//            if (b == null) {
//                headJson.put("result", "99");
//                headJson.put("des", "des解密失败");
//                return headJson.toString();
//            }
//            httpResponseData.setBodyJson(JSONObject.fromObject(new String(b)));
//            logger.info("bodyJSON:" + httpResponseData.getBodyJson().toString());
//        }
//        String act = httpResponseData.getBodyJson().optString("act");
//        if(!act.equalsIgnoreCase("cbuy")){
//            switch (tag) {
//                case "1201":
//                    taskPool.submit(new Handler(httpResponseData));
//                    break;
//                case "1203":
//                    taskPool.submit(new Handler(httpResponseData));
//                    break;
//                case "1206":
//                    taskPool.submit(new Handler(httpResponseData));
//                    break;
//                default:
//                    break;
//            }
//        }
//        return httpResponseData.toHttpMessage();
        return null;
    }


    private byte[] readRequest(HttpServletRequest request) throws IOException {
        int length = request.getContentLength();
        ServletInputStream is = request.getInputStream();
        byte[] buffer = new byte[length];
        is.read(buffer, 0, length);
        return buffer;
    }

    private void writeResponse(HttpServletResponse response, byte[] repData) throws IOException {
        ServletOutputStream os = response.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        dos.write(repData);
    }

    private String getRequestIpAddr(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
