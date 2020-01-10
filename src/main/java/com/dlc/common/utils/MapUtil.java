package com.dlc.common.utils;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Package com.dlc.common.utils
 * @Description: MapUtil
 * @Copyright: Copyright (c) 2017
 * @Author tangxs
 * @date 2018年3月10日 17:45
 * @version V1.0.0
 */
public class MapUtil {

    /**
     *  某一个参数是null  返回false
     * @param key
     * @return
     */
    public static boolean isBlank(Map<String,Object> map,String ... key){
        for (String s : key){
            if(!map.containsKey(key)){
                return false;
            }
            if(map.get(key) == null){
                return false;
            }
        }
        return true;
    }

    /**
     *  比较对象是否存在并且值等于  返回false
     * @param key
     * @return
     */
    public static boolean Compare(Map<String,Object> map,String key,String str){
        if(!map.containsKey(key)){
            return false;
        }
        if(map.get(key) == null){
            return false;
        }
        if(StringUtils.isEmpty(str)){
            return false;
        }
        if(!str.equals(map.get(key).toString())){
            return false;
        }
        return true;
    }

//    public static void main(String[] args) {
//        Map<String,Object> map = new HashMap<>();
//        map.put("status",1);
//        if(MapUtil.Compare(map,"status","1")){
//            System.out.println("true");
//        }else {
//            System.out.println("false");
//        }
//    }

}
