package com.wushi.lade.security;

import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;

/**
 * Hmac算法（散列消息鉴别码）
 *
 * @author wushi
 * @date 2020/1/3 0:08
 */
public class HMAC {

    /**
     * HmacMD5加密
     *
     * @param input 待加密字符串
     * @param key   密钥
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/3 0:12
     */
    public static String hmacMd5(String input, String key) {
        HMac hMac = new HMac(HmacAlgorithm.HmacMD5, key.getBytes());
        return hMac.digestHex(input);
    }

    /**
     * HmacSHA1加密
     *
     * @param input 待加密字符串
     * @param key   密钥
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/3 0:14
     */
    public static String hmacSha1(String input, String key) {
        HMac hMac = new HMac(HmacAlgorithm.HmacSHA1, key.getBytes());
        return hMac.digestHex(input);
    }

    /**
     * HmacSHA256加密
     *
     * @param input 待加密字符串
     * @param key   密钥
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/3 0:15
     */
    public static String hmacSha256(String input, String key) {
        HMac hMac = new HMac(HmacAlgorithm.HmacSHA256, key.getBytes());
        return hMac.digestHex(input);
    }

    /**
     * HmacSHA384加密
     *
     * @param input 待加密字符串
     * @param key   密钥
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/3 0:15
     */
    public static String hmacSha384(String input, String key) {
        HMac hMac = new HMac(HmacAlgorithm.HmacSHA384, key.getBytes());
        return hMac.digestHex(input);
    }

    /**
     * HmacSHA512加密
     *
     * @param input 待加密字符串
     * @param key   密钥
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/3 0:15
     */
    public static String hmacSha512(String input, String key) {
        HMac hMac = new HMac(HmacAlgorithm.HmacSHA512, key.getBytes());
        return hMac.digestHex(input);
    }
}
