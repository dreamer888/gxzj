package com.dlc.modules.qd.utils;

import com.dlc.common.utils.ConfigConstant;
import com.dlc.common.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Package com.dlc.modules.qd.utils
 * @Description: PhoneCodeVer 短信验证码
 * @Copyright: Copyright (c) 2017
 * Author tangxs
 * @date 2017/12/14 9:58
 * version V1.0.0
 */
@Component
public class
PhoneCodeVer {

    private static RedisUtils redisUtils;

    private static Logger logger = LoggerFactory.getLogger(PhoneCodeVer.class
    );

    @Autowired
    public void setRedisUtils(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    public static String getTkey(){
        //东八区GMT+8:00
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return simpleDateFormat.format(new Date());
    }

    public static String getPassword(String password,String tkey){

        //md5( md5(password) + tkey)) 密码加密规则
        String passwordMd5 = MD5Util.MD5Encode(MD5Util.MD5Encode(password,"UTF-8") + tkey,"UTF-8");
        return passwordMd5;
    }

    public static String getCode(String phoneNum){

        String code = (int)((Math.random()*9+1)*1000)+"";
        redisUtils.set(phoneNum,code,600);
        return code;

    }

    /**
     * @Package com.dlc.modules.qd.utils
     * @Description: PhoneCodeVer
     * 中国移动   134，135，136，137，138，139
     *          141，143，147，148
     *          150，151，152，154，157，158，159
     *          1703，1705，1706，
     *          178
     *          182，183，184，187，188
     * 中国联通   130，131，132•
     *          145
     *          155，156
     *          1704，1707，1708，1709
     *          171，175，176
     *          185，186
     *中国电信    133
     *          149
     *          153
     *          1700，1701，1702
     *          173，177
     *          180，181，189
     * @Copyright: Copyright (c) 2017
     * Author tangxs
     * @date 2017/12/14 12:10
     * version V1.0.0
     */
    public static boolean isPhoneNum(String phone){

        String regex = "^((13[0-9])|(14[135789])|(15([0-9]))|(17[0135678])|(18[0-9]))\\d{8}$";
        if(phone.length() != 11){
            return false;
        }else{
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if(isMatch){
                return true; //符合规则
            } else {
                return false;
            }
        }
    }
    
   // ------------------身份证格式的正则校验----------------->
    public static boolean isIdCode(String idCode) {
        String reg = "^\\d{15}$|^\\d{17}[0-9Xx]$";
        if (!idCode.matches(reg)) {
            return false;
        }
        return true;
    }

    public static String sendCode(String phoneNum){

        if(isPhoneNum(phoneNum)){
            String result = null;
            try {
                //result = SendPushPost.sendGet(getUrl(phoneNum));
                String content = "你好，你的验证码是:"+getCode(phoneNum)+"【个联智能】";
                content = URLEncoder.encode(content, "UTF-8");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("action",ConfigConstant.ACTION);
                map.put("account", ConfigConstant.ACCOUNT_NUM);
                map.put("password", ConfigConstant.PASSWORD);
                map.put("mobile", phoneNum);
                map.put("content", content);
                map.put("extno", ConfigConstant.EXTNO);
                map.put("rt", ConfigConstant.RT);
                result = SendPushPost.sendPost(ConfigConstant.PHONE_VER_URL, map);
                logger.info("sendSms result-->"+result);
            } catch (Exception e) {
                logger.info("发送短信报错：",e);
            }
            return result;
        }
        return null;
    }

    public static String sendErrorMsg(String phoneNum,String msg){

        if(isPhoneNum(phoneNum)){
            String result = null;
            try {
                //result = SendPushPost.sendGet(getUrl(phoneNum));
                String content = msg+"【个联智能】";
                content = URLEncoder.encode(content, "UTF-8");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("action",ConfigConstant.ACTION);
                map.put("account", ConfigConstant.ACCOUNT_NUM);
                map.put("password", ConfigConstant.PASSWORD);
                map.put("mobile", phoneNum);
                map.put("content", content);
                map.put("extno", ConfigConstant.EXTNO);
                map.put("rt", ConfigConstant.RT);
                result = SendPushPost.sendPost(ConfigConstant.PHONE_VER_URL, map);
                logger.info("sendSms result-->"+result);
            } catch (Exception e) {
                logger.info("发送短信报错：",e);
            }
            return result;
        }
        return null;
    }

    public static String getUrl(String phoneNum) throws UnsupportedEncodingException {
        String password="XUac2018";
        String username = "13466789109";
        String productId = "676767";
        String content = "你好，你的验证码是:"+getCode(phoneNum)+"【开心拼车】";
	    content = URLEncoder.encode(content, "UTF-8");

        String tkey = getTkey();
        String url = ConfigConstant.PHONE_VER_URL+"?username="+username+"&tkey="+tkey
                +"&password="+getPassword(password,tkey)+"&mobile="+phoneNum
                +"&content="+content+"&productid="+productId+"&xh=";
        logger.debug("url:"+url);
        return url;
    }

//    public static void main(String[] args) throws InterruptedException {
////        System.out.printf(getPassword("123456","20160315120530"));
//        //String str =SendPushPost.sendPost("http://adm.hbfyun.com/pijian/start/",
//               // "macno=1000100010001111&sysnum=1&time=" +
//                //"1&money=100&sign=a0fa4f0a47c5cd26660d0607085bf83b&type=1&pipe=1");
//        //System.out.println("args = [" + str + "]");
////        String[] r = "\n1,201712141443291718".split(",");
////        System.out.println("r = [" + r[0] + "]");
////        PhoneCodeVer.sendCode("13316670597");
//    }

}
