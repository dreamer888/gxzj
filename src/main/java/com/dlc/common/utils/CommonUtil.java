package com.dlc.common.utils;


import com.dlc.modules.qd.utils.MyConfig;
import com.github.wxpay.sdk.WXPayUtil;
import net.sf.json.JSONArray;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

@Component
public class CommonUtil {

	private static RedisUtils redisUtils;
	private static Logger log = LoggerFactory.getLogger(CommonUtil.class);

	@Autowired
	public void setRedisUtils(RedisUtils redisUtils) {
		this.redisUtils = redisUtils;
	}
	/**
	 * 发送https请求
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return 返回微信服务器响应的信息
	 */
	public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded"); 
			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			return buffer.toString();
		} catch (ConnectException ce) {
			log.error("连接超时：{}", ce);
		} catch (Exception e) {
			log.error("https请求异常：{}", e);
		}
		return null;
	}

    /**
     * 需要证书的请求
     * @param strUrl String
     * @param reqData 向wxpay post的请求数据  Map
     * @param connectTimeoutMs 超时时间，单位是毫秒
     * @param readTimeoutMs 超时时间，单位是毫秒
     * @return API返回数据
     * @throws Exception
     */
    public static String requestWithCert(String strUrl, SortedMap<String, String> reqData,
                                         int connectTimeoutMs, int readTimeoutMs) throws Exception {
        MyConfig config = new MyConfig();
        String UTF8 = "UTF-8";
        String reqBody = WXPayUtil.mapToXml(reqData);
        URL httpUrl = new URL(strUrl);
        char[] password = config.getMchID().toCharArray();
        InputStream certStream = config.getCertStream();
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(certStream, password);

        // 实例化密钥库 & 初始化密钥工厂
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, password);

        // 创建SSLContext
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();

        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setConnectTimeout(connectTimeoutMs);
        httpURLConnection.setReadTimeout(readTimeoutMs);
        httpURLConnection.connect();
        OutputStream outputStream = httpURLConnection.getOutputStream();
        outputStream.write(reqBody.getBytes(UTF8));

        // if (httpURLConnection.getResponseCode()!= 200) {
        //     throw new Exception(String.format("HTTP response code is %d, not 200", httpURLConnection.getResponseCode()));
        // }

        //获取内容
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, UTF8));
        final StringBuffer stringBuffer = new StringBuffer();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
        }
        String resp = stringBuffer.toString();
        if (stringBuffer!=null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                // e.printStackTrace();
            }
        }
        if (inputStream!=null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                // e.printStackTrace();
            }
        }
        if (outputStream!=null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                // e.printStackTrace();
            }
        }
        if (certStream!=null) {
            try {
                certStream.close();
            } catch (IOException e) {
                // e.printStackTrace();
            }
        }
        // if (httpURLConnection!=null) {
        //     httpURLConnection.disconnect();
        // }

        return resp;
    }



	public static String urlEncodeUTF8(String source){
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static List<Map> getJson(HttpServletRequest request){
		String mapstr = redisUtils.get("address");
		if(!StringUtils.isEmpty(mapstr)){
			List<Map> maps= (List) JSONArray.fromObject(mapstr);
			return maps;
		}
		String dir = request.getSession().getServletContext()
				.getRealPath("/address.json");
		try {
			File file = new File(dir);
			if (!file.exists()) {
				file.createNewFile();
			}
			String str= FileUtils.readFileToString(file, "UTF-8");
			List<Map> maps= (List) JSONArray.fromObject(str);
			redisUtils.set("address",maps);
			return maps;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getProvinceName(List<Map> maps,String provinceId){
		for (Map p : maps){
			String province_id = p.get("province_id")+"";
			if(province_id.equals(provinceId)){
				String pro = (String)p.get("name");
				if(pro.contains("省")){
					return (String)p.get("name");
				}
				return p.get("name")+" 省级";
			}
		}
		return null;
	}
	public static String getCityName(List<Map> maps,String cityId,String provinceId){
		for (Map p : maps){
			List<Map> citys = (List) p.get("city");
			for (Map c : citys){
				String city_id = c.get("city_id")+"";
				String province_id = c.get("province_id")+"";
				if(city_id.equals(cityId)){
					if(!provinceId.equals(province_id)){
						return null;
					}
					String city = (String)c.get("name");
					if(city.contains("市")){
						return (String)c.get("name");
					}
					return c.get("name") +" 市级";
				}
			}
		}
		return null;
	}
	public static String getAreaName(List<Map> maps,String areasId,String cityId){
		for (Map p : maps){
			List<Map> citys = (List) p.get("city");
			for (Map c : citys){
				List<Map> areas = (List) c.get("area");
				for (Map a : areas){
					String area_id = a.get("area_id") + "";
					String city_id = a.get("city_id") + "";
					if(area_id.equals(areasId)){
						if(!cityId.equals(city_id)){
							return null;
						}
						String area = (String)a.get("name");
						if(area.contains("县")){
							return (String)a.get("name");
						}
						return a.get("name") + " 县级";
					}
				}
			}
		}
		return null;
	}

}