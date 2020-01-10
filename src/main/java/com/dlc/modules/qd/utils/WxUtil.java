package com.dlc.modules.qd.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class WxUtil {

    private static final String WX_REDIRECT = "http://gxzjj.j.xiaozhuschool.com/wx/getOpenId";

    /**
     * 获取微信code链接地址
     *
     * @return 链接地址
     * @throws Exception
     */
    public static String getCode() throws Exception {
        MyConfig config = new MyConfig();
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                "appid=" + config.getAppID() + "" +
                "&redirect_uri=" + WX_REDIRECT + "" +
                "&response_type=code" +
                "&scope=snsapi_base" +
                "&state=STATE#wechat_redirect";
        return url;
    }

    /**
     * 获取微信openid链接地址
     *
     * @param code 通过微信返回的code
     * @return 链接地址
     * @throws Exception
     */
    public static String getOpenId(String code) throws Exception {
        MyConfig config = new MyConfig();
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + config.getAppID() + "" +
                "&secret=" + config.getAppSecret() + "" +
                "&code=" + code + "" +
                "&grant_type=authorization_code";
        return url;
    }

    public static JSONObject doGetJson(String url) throws IOException {
        JSONObject jsonObject = null;
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            String result = EntityUtils.toString(entity, "UTF-8");
            jsonObject = JSONObject.parseObject(result);
        }
        httpGet.releaseConnection();
        return jsonObject;
    }
}
