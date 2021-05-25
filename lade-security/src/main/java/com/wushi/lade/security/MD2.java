package com.wushi.lade.security;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * MD2算法
 * 注：摘要算法不可逆，所以无解密方法
 *
 * @author wushi
 * @date 2020/1/2 23:44
 */
public class MD2 {

    /**
     * MD2加密
     *
     * @param input 待加密字符串
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/2 23:54
     */
    public static String md2Encrypt(String input) {
        Digester md2 = new Digester(DigestAlgorithm.MD2);
        return md2.digestHex(input);
    }
}
