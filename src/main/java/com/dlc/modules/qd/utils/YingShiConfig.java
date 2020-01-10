package com.dlc.modules.qd.utils;

import com.github.wxpay.sdk.WXPayConfig;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class YingShiConfig{

    public YingShiConfig() throws Exception {

    }

    /**获取萤石token请求地址*/
    public static final String GET_TOKEN_URL = "https://open.ys7.com/api/lapp/token/get";

    /**appKey*/
    public static final String getAppKey() {
        return "a819fd9306524fba86e62bbf83c01633";
    }
    /**appSecret*/
    public static final String getAppSecret() {
        return "68f83d19255e21759e753fec5ed79c02";
    }


    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }


}