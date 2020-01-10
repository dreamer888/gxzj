package com.dlc.common.utils;

/**
 * @author chenyuexin
 * @version 1.0
 * @date 2018-05-31 08:47
 */

import java.util.Random;

/**
 * 随机数工具
 */
public class RandomUtils {

    public static int[] nums = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    public static Random random = new Random();

    /**
     * 生成随机数字
     *
     * @param length 随机数长度
     * @return
     */
    public static String randomNumber(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(10);
            builder.append(nums[index]);
        }
        return builder.toString();
    }
}