package com.wushi.lade.security;

import cn.hutool.crypto.digest.DigestUtil;

/**
 * MD5算法
 * 注：摘要算法不可逆，所以无解密方法
 *
 * @author wushi
 * @date 2020/1/2 23:44
 */
public class MD5 {

    /**
     * MD5加密
     *
     * @param input       待加密字符串
     * @param use16Length 是否返回长度为16位的加密结果
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/2 23:54
     */
    public static String md5Encrypt(String input, boolean use16Length) {
        return use16Length ? DigestUtil.md5Hex16(input) : DigestUtil.md5Hex(input);
    }

    /**
     * MD5加密（32位）
     *
     * @param input 待加密字符串
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/2 23:49
     */
    public static String md5Encrypt(String input) {
        return md5Encrypt(input, false);
    }
}
