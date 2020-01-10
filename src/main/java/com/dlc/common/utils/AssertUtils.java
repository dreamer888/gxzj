package com.dlc.common.utils;

/**
 * @author chenyuexin
 * @version 1.0
 * @date 2018-05-31 08:48
 */
public class AssertUtils {

    public static void requireTrue(boolean value, String msg) {
        if (!value) {
            throw new RuntimeException(msg);
        }
    }

}