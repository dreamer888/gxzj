/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: IntegerUtil
 * Author:   Administrator
 * Date:     2018/5/30 19:10
 * Description: Integer 工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.dlc.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chenyuexin
 * @date 2018-05-30 19:10
 * @version 1.0
 */
public class StringUtil {
    private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);
    /**
     * 把心跳上传的经度转换为GPS经度
     * @param longitude
     * @return
     */
    public static String getRealLongitude(String longitude) {
        if(!IntegerUtil.isContainNumber(longitude)){
            longitude = "0";
        }else if(longitude.length() > 4){
            String prifix = longitude.substring(0,3);//取到经度小数点前三位
            String suffix = longitude.substring(3); //取到经度小数点后面位数
            Float sufFloat = Float.valueOf(suffix)/60;
            sufFloat = Float.parseFloat(prifix) + sufFloat;
            longitude = sufFloat.toString();
        }

        return longitude;
    }

    /**
     * 把心跳上传的经度转换为GPS经度
     * @param latitude
     * @return
     */
    public static String getRealLatitude(String latitude) {
        if(!IntegerUtil.isContainNumber(latitude)){
            latitude = "0";
        }else if(latitude.length()>3){
            String prifix = latitude.substring(0,2);//取到纬度小数点前两位
            String suffix = latitude.substring(2); //取到纬度小数点后面位数
            Float sufFloat = Float.valueOf(suffix)/60;
            sufFloat = Float.valueOf(prifix) + sufFloat;
            latitude = sufFloat.toString();
        }
        return latitude;
    }

//    public static void main(String[] args) {
//        String longitude = "11343.264204";
//
//        String latitude = "2259.399623";
//
//        getRealLongitude(longitude);
//
//        getRealLatitude(latitude);
//    }
}
