package com.dlc.modules.qd.utils;

import com.github.wxpay.sdk.WXPayConfig;
import org.apache.poi.hssf.record.pivottable.StreamIDRecord;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

public class MyConfig implements WXPayConfig {

    private byte[] certData;

    public MyConfig() throws Exception {
       // URL resource = MyConfig.class.getClassLoader().getResource("test/apiclient_cert.p12");
        String certPath = "/javadata/DGJAVA/tomcat/cert/gxzjj/test/apiclient_cert.p12";
       // String path = resource.getPath();
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    /**
     * 支付宝支付相关信息
     */
    public static final String ZFB_NOTIFYURL = "http://gxzjj.j.xiaozhuschool.com/api/pay/zfbNotify";
    public static final String ZFB_APPID = "2018052460188360";
    public static final String ZFB_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCgWo6GucdvhxbvpdSOTrQ3njnV0y4BaJLgwHppX4BOrRW1rLAlqVy0yNY1YBL6814CHJ2QKZpMkZYMsDSVeQbwbGAJIujzOEj9m/CWlHbhvwh76r97agNMK+K/DtbvgS/5yQ61wz+v43ZObU/Bp2gkQOADSaGAqTaxpbMhunoBDaVTuiYsdbqD6p54jfhx/75SMPgDb+7D/g3SdrUXixRo3DFNtf/9zkuc51hD+HUqQUinN0pprxfKDSKE99/I/n1BvbPw7zKrPwykTWVLOBap5g/9QexDYVdUWyVHL0m3LqtAUntbZyNC9f4DJLeXpCfL+R6ob8Q9xnHbkm9j6nr7AgMBAAECggEARiz7XPim0Cc1oXFYYWMOOZz0QKCn7JPgW53AtKbH1yambpiBiinhWXjfTgKaq9qf1JrDVdjvHv1Dxo/Co3uQzt0O9lCh0/dY8iwxkSSuFkNtKhXk8NTHVqmKFQUgiTZnUZBs8JzfHVGVb+Mk2WIzEFKX9rhdhpoHRp12lJcwV473TAbg+FQx89cffvKf1JJomNFMj6AiBvH80DD4rYF/q/p/Pb8Zsvcjc3EsT7FbkmT1ZkQ1HwN/erqCoG1mBB8Lxdy7XdFD9HK/gIMPdrmDrZUWKUShg7UkDnjtcuW+jnjflIWFre5Dekv7Vq4B5X02J/Nefd4R+9xLK1kXgkgjgQKBgQDn3tkKy2DojJjQhS68dqauCtgYJzMTQMyCzU7mAknW8Sv+sXtFXYjiKfrLNXd4C8DOHgyl5T4OAVcUY4OKgg/HYGcsQgaH/TzC6xIbOI+ot7ldyKXpq99/GfHqGviyYhmnWp7jp7iUC4hQ4v8WUTVtUZHTywJZtZ00whqOeYKMuwKBgQCxCnb4fI2p5eMIXoQQYvlVTLZlMbIeT+7FglUZdQts0IHRQ37K/s4vys00tejdT1bFNzj7nzjmT52GJarLUNShXFbrs1SU0GuhX07xsRu/L/8fmLkDC0TCP1je9AYX5/g6qYp6i9i/dteHvRFfTOKI1yoRDz78US0F3rm3YXYGwQKBgQDS1kf1EMmlq3Ko6PF+XUVirGmiTMKclydfKOC9NQd/cvzWMvwsLsjxHcr2TD+94LIIujo1/yVWBtHvsC1EHM/MM1SdYcwei55cfvOP3PaEK8taA8EZoTcwk57tiGjri1hYXIZt62xi8bmaE05wNJeoN6uqMcaJXCL+fiE3vDWBUQKBgDIaRw8NmL3TPEwbC37js7JLMncy+DiR4H3u44x/zkruOz7wvgpO0CVAXJClRpCTKnhHp422QtETCP503IALkRFVY5fyuWYgEcWqhlnqCHMz7xyPCRTudHgQm2MFZlAPqvVXE8SYnS+aP2a/wBHbXWTil5oaWXXotzgpDx/xqghBAoGABsguDVJPaRiPusZFWzlS8hsfi1yZEwJxn4jVNO07mtQtnTEYv/W5Q2ugR9ww8VrqmNiRM363+sX+s2Dt9i4xOtV8Oizc4JcrihU05WuK1zUS60MYyHgM/4JPIQLppTsarSGKvQmgXNX68Gfw3jWK2hCdJ0q3q67gIcNms1QsuDM=";
    public static final String ZFB_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoi1t1dnSiNyHGt/COh9PHheqiHC1pnqvXqCWHjLD/q/066J4Y2uZ6mGgoRgue0heFZR+93HJX+R3o9UKW2Yc8ID6b9Bo4xyC12btvU73hdlO6HrwrZJwS9mUOr+0te3i6Wiyiry4mykJvWsJxffVF9r3wypUzsPhhw4EvX7XQR5a0e5LCv2jdrklubm1FtziI3+8ytoPXn43U0Rw4wYjRRY9y+hcfXl3X1VwoQ/rOP088yTPhhV5NlJlAh+Fo32LJjz7VsQagI0flKUORYHXo9rO9B0w6dc+EoY6ouZG6qjoW7CuwqmNTGLMJ/RivLhbRtilf2MPb5Uha9ukF8JnnQIDAQAB";
    public static final String ZFB_SIGN_TYPE = "RSA2";
    public static final String ZFB_GATEWAY = "https://openapi.alipay.com/gateway.do";
    public static final String ZFB_CHARSET = "utf-8";
    public static final String ZFB_OBJECT = "json";

    //迪尔西测试公众号2
    public String getAppID() {
        return "wx2c49fec17dc24b80";
    }

    //商户号
    public String getMchID() {
        return "1507957931";
    }

    //充值reappid
    public String getReAppId() {
        return "";
    }
    //充值reMchId
    public String getReMchId() {
        return "";
    }
    //充值商户号
    public String getAppSecret() {
        return "090250d12824be27d7c159361e7347e0";
    }

    public String getKey() {
        return "xiaozhuschoolxiaozhuschoolxiaozh";
    }

        public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    /**
     * 微信支付接口地址
     */
    //微信支付统一接口(POST)
    public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //微信退款接口(POST)
    public final static String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    //订单查询接口(POST)
    public final static String CHECK_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
    //关闭订单接口(POST)
    public final static String CLOSE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
    //退款查询接口(POST)
    public final static String CHECK_REFUND_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
    //对账单接口(POST)
    public final static String DOWNLOAD_BILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";
    //短链接转换接口(POST)
    public final static String SHORT_URL = "https://api.mch.weixin.qq.com/tools/shorturl";
    //接口调用上报接口(POST)
    public final static String REPORT_URL = "https://api.mch.weixin.qq.com/payitil/report";
    //微信公众号支付获取openId
    public final static String oauth_url = "https://api.weixin.qq.com/sns/oauth/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

}