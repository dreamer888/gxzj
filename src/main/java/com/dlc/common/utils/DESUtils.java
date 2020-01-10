package com.dlc.common.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 * des加解密处理工具类
 * 
 * @author hl
 */
public class DESUtils
{
    private static final String DES = "DES";
    private static SecureRandom random = new SecureRandom();
    private static Cipher cipher;

    public static byte[] encrypt(byte[] data, String password) {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            SecretKey key = keyFactory.generateSecret(desKey);
            cipher = Cipher.getInstance(DES);
            cipher.init(Cipher.ENCRYPT_MODE, key, random);
            return cipher.doFinal(data);
        }
        catch (Exception e) {
            return null;
        }
    }

    public static byte[] decrypt(byte[] data, String password) {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            SecretKey key = keyFactory.generateSecret(desKey);
            cipher = Cipher.getInstance(DES);
            cipher.init(Cipher.DECRYPT_MODE, key, random);
            return cipher.doFinal(data);
        }
        catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        String content = "{'user':'10000', 'pass':'048a0c6e1439960c1faa9256f7212ee6'}";
        String password = "12345678";
        System.out.println("content  : " + content);
        System.out.println("password : " + password);
        // 加密
        byte[] encData = DESUtils.encrypt(content.getBytes(), password);
        System.out.println("加密后内容为wzh：" + encData.length + ":" + new String(encData));
        // 解密
        byte[] decData = DESUtils.decrypt(encData, password);
        System.out.println("解密后内容为wzh：" + decData.length + ":" + new String(decData));
    }
}
