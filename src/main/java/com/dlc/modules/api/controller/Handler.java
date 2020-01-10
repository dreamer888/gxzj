package com.dlc.modules.api.controller;


import com.dlc.common.constant.Constants;
import com.dlc.common.utils.HttpResponseData;
import com.dlc.common.utils.SendUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * 第三方客户业务处理类，需自行修改处理逻辑
 * 
 * @author hl
 */
public class Handler extends Thread {

    private final Logger logger = LoggerFactory.getLogger(GLController.class);

    private HttpResponseData httpResponseData;

    public Handler(HttpResponseData httpResponseData) {
        this.httpResponseData = httpResponseData;
    }

    /**
     * 此为测试代码处理逻辑，需修改成具体业务处理逻辑
     */
    @Override
    public void run() {
        //TODO 
        logger.info("Handle headJSON:" + httpResponseData.getHeadJson().toString());
        logger.info("Handle bodyJSON:" + httpResponseData.getBodyJson().toString());
        String imei = httpResponseData.getImei();
        if (StringUtils.isEmpty(imei)) {
            imei = "ic5b47124a95eb9";
        }
        JSONArray imeis = new JSONArray();
        imeis.add(imei);
        if (httpResponseData.getBodyJson().size() == 0) {
            SendUtils.send1202Data(httpResponseData.getMessage(), imeis, httpResponseData.getHeadJson().toString());
        }
        else {
            String act = httpResponseData.getBodyJson().optString("act");
            if(!act.equalsIgnoreCase("cbuy")){
                Long time = new Date().getTime()/1000;
                JSONObject obj = new JSONObject();
                obj.put(Constants.ACT,act);
                obj.put(Constants.IMEI, imei);
                obj.put(Constants.TSP,time);
                obj.put(Constants.RESULT, 0);
                logger.info("return message--->"+obj.toString());
                SendUtils.send1202Data(httpResponseData.getMessage(), imeis, obj.toString());
            }
        }

    }
}
