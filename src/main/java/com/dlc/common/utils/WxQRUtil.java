package com.dlc.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.dlc.modules.qd.utils.SendPushPost;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static com.dlc.modules.qd.utils.GetWeiXinCode.getAccessToken;

/**
 * @author chenyuexin
 * @version 1.0
 * @date 2018-07-20 10:00
 */
public class WxQRUtil {

    //获取运营二维码
    public static String getQRCodeImg(String appSecret, String imei, String openid, String appId, String deviceNo,String freeCode) {

        String access_token = getAccessToken(appId, appSecret);
        // 2.获取公众号的二维码
        //2.1 获取 ticket
        TreeMap<String, String> params = new TreeMap<>();
        params.put("access_token", access_token);
        //output data

        StringBuffer sb = new StringBuffer();
        StringBuffer append = sb.append(deviceNo).append("@@").append(imei).append("@@").append(openid).append("@@").append(appId).append("@@").append(freeCode);
        String strParams = append.toString();

        Map<String, String> intMap = new HashMap<>();
        intMap.put("scene_str", strParams);
        Map<String, Map<String, String>> mapMap = new HashMap<>();
        mapMap.put("scene", intMap);

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("action_name", "QR_STR_SCENE");
        paramsMap.put("expire_seconds", "2592000");
        paramsMap.put("action_info", mapMap);
        String data = new Gson().toJson(paramsMap);
        String showqrcode_path = SendPushPost.sendPost(ConfigConstant.ticket + access_token, data);
        System.out.print(showqrcode_path);
        //2.2 获取二维码
        JSONObject jsonObject = JSONObject.parseObject(showqrcode_path);
        System.out.print(jsonObject.get("ticket"));

        return (String) jsonObject.get("url");

    }


}
