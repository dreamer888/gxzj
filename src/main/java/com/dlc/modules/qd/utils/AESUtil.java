package com.dlc.modules.qd.utils;

import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "AES";
    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS5Padding";
    /**
     * 生成key        2IBtBXdrqC3kCBs4gaceL7nl2nnFadQv
     *                          2IBtBXdrqC3kCBs4gaceL7nl2nnFadQv
     */
    private static SecretKeySpec key = new SecretKeySpec(MD5.MD5Encode("2IBtBXdrqC3kCBs4gaceL7nl2nnFadQv").toLowerCase().getBytes(), ALGORITHM);

    /**
     * AES加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptData(String data) throws Exception {
        // 创建密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
        // 初始化  
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64Util.encode(cipher.doFinal(data.getBytes()));
    }

    /**
     * AES解密
     *
     * @param base64Data
     * @return
     * @throws Exception
     */
    public static String decryptData(String base64Data) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64Util.decode(base64Data)));
    }

    public static void main(String[] args) throws Exception {
        try {
            BASE64Encoder base64 = new BASE64Encoder();
            MyConfig config = new MyConfig();
            String req_infoStr =  "lZY6mkpcLVgQ2OKlwFxktarkg2s6bDO3CUr5/RP774HykL6f7K07maSDz20bOa+URcDST+nLKrzwwretGrORJuIrKdYK0g3wYk7PXLEEuOHl/FDjFML48LXTyXiG72O1vTwONKmSsQLqZ0dirgrJQd2GcIZ18oYdPKp8XI4Rxnp9t7amhnqH4p85un/rqVaeYXHF8GO1v3AWuXU7YhpsYmwcCxBny32x4PEUFnv/siooYxKugfRfut72yM3Bgj5B45xWCF2k9bGHY4wFGx5wrSt6908Bm8XRzDdg7AKnBfd9p+WaaxbVRhEeriPZoSpPFfw5mOkq9OQelEAWKrsMe0SIL1v3156GFwyYCIWNssfcWzt4FH7vPYJHNHE+nbHxKqCe0JQ0jdk4iaH4KxXcEuzGgbmZFmBmBx3qbPt9L+GJqlPrGVloYEuqcEQuvVqxTIAaKq/OKg3yJCZM2d9f18GwbZPgT7amx4hfrn7qVTz0w3+ryqUKtqb3v5nS7n7dkqGvUbLgBo1ghiSHRLN41npG2rvmEZ9H3zUZOud2bz/0Sie5J/JwOCzr7gV2fgTGKvP9p1Qqm3T7mTvst1EAq+igMY9DCh8VKW+q0DLSWKRAKH8gLT7AuESKs8SXOLVUXGOavICAFR3cEzZdSx5LVED/XBUDFW+9PMSPtd4jrImyfYzylmlSLCIIubR2Wj8yIpfXQTsrVG5Em9VrKRROn7JU8jeZVqtuwtCXCLHx49d2dWDMr8CRydvEKdCD0DZCzrpkAWdaj/mHylmUulskHIDC48T3VQbO5i4hNx1IlBJaCvX+V/oPbIoKBp44NbeN3fXqv4VcDw9Z9k13J9rE9O1UNY96tsN28Ddfo/wxsuOvnFkdCewrxW9e37uvlPQLVT7WFklTLNHb08fRgKqLMQVXyoy6Tq0JOZN+tAwDhzUCloa3wANL1evIOoTHHDON1nazEmkEoS0u1kZ2Ft5JjZdhN/KRaAxCLGPaBkW0HTeB0Yqku1UXf8qg61WLJ7RozkXXcq64cXy8/1KXFvZ/DnWtmBIdRWRqKPefa4uq7os=";
            String reqInfo = WxPayUtils.decryptData(req_infoStr);

            System.out.printf("reInfo:"+reqInfo);
        }catch (Exception e){
            System.out.printf(e.getMessage());
        }
    }

}