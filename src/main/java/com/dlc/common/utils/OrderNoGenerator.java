package com.dlc.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * 订单号生成器
 */
public class OrderNoGenerator {

    /**
     * 订单号生成器
     * <p>
     * 为了保证生成性能需满足条件size<10^length/4
     *
     * @param size   保证连续不重读的数
     * @param length 生成随机数的长度
     */
    public static String getOrderIdByTime() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate=sdf.format(new Date());
        String result="";
        Random random=new Random();
        for(int i=0;i<3;i++){
            result+=random.nextInt(10);
        }
        return newDate+result;
    }

}