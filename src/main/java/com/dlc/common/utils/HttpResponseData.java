package com.dlc.common.utils;


import com.dlc.common.constant.Constants;
import net.sf.json.JSONObject;

/**
 * 解析数据实体类
 * 
 * @author hl
 */
public class HttpResponseData {
    private String code;
    private String imei;
    private String message;
    private JSONObject headJson;
    private JSONObject bodyJson;

    public String getImei()
    {
        return imei;
    }

    public void setImei(String imei)
    {
        this.imei = imei;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public HttpResponseData(String code, JSONObject headJson) {
        this.code = code;
        this.message = headJson.optString("message");
        this.imei = headJson.optString("imei");
        this.headJson = headJson;
    }

    public String getCode()
    {
        return code;
    }

    public JSONObject getHeadJson()
    {
        return headJson;
    }

    public void setHeadJson(JSONObject headJson)
    {
        this.headJson = headJson;
    }

    public JSONObject getBodyJson() {
        if (bodyJson == null) {
            return new JSONObject();
        }
        return bodyJson;
    }

    public void setBodyJson(JSONObject bodyJson)
    {
        this.bodyJson = bodyJson;
    }

    public String toHttpMessage() {
        JSONObject _json = new JSONObject();
        _json.put(Constants.CODE, code);
        _json.put(Constants.MESSAGE, message);
        _json.put(Constants.RESULT, 0);
        return _json.toString();
    }

}
