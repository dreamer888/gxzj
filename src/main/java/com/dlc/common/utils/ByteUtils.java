package com.dlc.common.utils;

/**
 * btye数组处理工具类
 * 
 * @author hl
 */
public class ByteUtils {

    public static byte[] subBytes(byte[] src, int startIndex) {
        return subBytes(src, startIndex, src.length - startIndex);
    }

    public static byte[] subBytes(byte[] src, int startIndex, int length) {
        if (startIndex >= src.length) {
            return null;
        }
        byte[] data = new byte[length];
        System.arraycopy(src, startIndex, data, 0, length);
        return data;
    }
}
