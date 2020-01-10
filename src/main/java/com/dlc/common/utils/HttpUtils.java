package com.dlc.common.utils;

import com.dlc.common.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * http\
 * https请求处理工具类
 * @author hl
 */
public class HttpUtils {
    private final static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public static String postHttpOrHttps(String url, byte[] sendData, int timeout) throws Exception {
//        logger.info("HttpUtils--send");
        String resultData = null;
        if (url.startsWith("https")) {
            resultData = postHttps(url, sendData, timeout);
        }
        else {
            resultData = postRequest(url, sendData, timeout);
        }
        logger.info("HttpUtils--resp data:" + resultData);
        return resultData;
    }

    private static String postRequest(String url1, byte[] sendData, int timeout) throws Exception {
        StringBuilder response = new StringBuilder();
        URL url = new URL(url1);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(timeout);
        conn.setReadTimeout(timeout);
        conn.setRequestMethod(Constants.POST);
        conn.setRequestProperty("Content-length", String.valueOf(sendData.length));
        conn.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.write(sendData);
        out.flush();
        out.close();

        conn.getResponseCode();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String temp;
        while ((temp = br.readLine()) != null) {
            response.append(temp);
        }
        return response.toString();
    }

    private static class TrustAnyTrustManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers()
        {
            return new X509Certificate[] {};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session)
        {
            return true;
        }
    }

    private static String postHttps(String url, byte[] sendData, int timeout) throws Exception {
        TrustManager[] tm = { new TrustAnyTrustManager() };
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, tm, new java.security.SecureRandom());
        URL console = new URL(url);
        console.openConnection().setConnectTimeout(timeout);
        console.openConnection().setReadTimeout(timeout);
        HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
        conn.setSSLSocketFactory(sc.getSocketFactory());
        conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod(Constants.POST);
        conn.connect();
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.write(sendData);
        // 刷新、关闭
        out.flush();
        out.close();
        InputStream is = conn.getInputStream();
        if (is != null) {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            is.close();
            String rep = new String(outStream.toByteArray());
            return rep;
        }
        return "error";
    }
}
