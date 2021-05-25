package com.wushi.lade.security;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.Digester;

/**
 * SHA算法
 * 注：摘要算法不可逆，所以无解密方法
 *
 * @author wushi
 * @date 2020/1/2 23:59
 * @description
 */
public class SHA {

    /**
     * SHA1加密
     *
     * @param input 待加密字符串
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/3 0:03
     * @description
     */
    public static String sha1Encrypt(String input) {
        return DigestUtil.sha1Hex(input);
    }

    /**
     * SHA256加密
     *
     * @param input 待加密字符串
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/3 0:05
     * @description
     */
    public static String sha256Encrypt(String input) {
        return DigestUtil.sha256Hex(input);
    }

    /**
     * SHA384加密
     *
     * @param input 待加密字符串
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/3 0:07
     * @description
     */
    public static String sha384Encrypt(String input) {
        Digester sha384 = new Digester(DigestAlgorithm.SHA384);
        return sha384.digestHex(input);
    }

    /**
     * SHA512加密
     *
     * @param input 待加密字符串
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/3 0:08
     * @description
     */
    public static String sha512Encrypt(String input) {
        Digester sha512 = new Digester(DigestAlgorithm.SHA512);
        return sha512.digestHex(input);
    }


}
