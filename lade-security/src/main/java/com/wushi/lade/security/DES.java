package com.wushi.lade.security;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

import java.util.Arrays;

/**
 * DES算法
 *
 * @author wushi
 * @date 2020/1/2 14:35
 */
public class DES {

    /**
     * DES加密算法
     *
     * @param input      待加密的字符串
     * @param encryptKey 密钥
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/2 14:47
     */
    public static String encrypt(String input, String encryptKey) {
        SymmetricCrypto des = new SymmetricCrypto(SymmetricAlgorithm.DES, Base64.decode(encryptKey));
        return des.encryptBase64(input);
    }

    /**
     * DES解密算法
     *
     * @param input      已加密字符串
     * @param decryptKey 密钥
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/2 14:56
     */
    public static String decrypt(String input, String decryptKey) {
        SymmetricCrypto des = new SymmetricCrypto(SymmetricAlgorithm.DES, Base64.decode(decryptKey));
        return des.decryptStr(input);
    }

    /**
     * DES随机生成密钥
     *
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/2 14:59
     */
    public static String generateKey() {
        //随机生成密钥
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.DES.getValue()).getEncoded();
        return Base64.encode(key);
    }

}
