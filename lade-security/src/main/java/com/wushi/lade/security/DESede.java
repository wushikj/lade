package com.wushi.lade.security;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

/**
 * DESede算法
 *
 * @author wushi
 * @date 2020/1/2 16:05
 */
public class DESede {

    /**
     * DESede加密
     *
     * @param input      待加密的字符串
     * @param encryptKey 密钥
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/2 16:13
     */
    public static String encrypt(String input, String encryptKey) {
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.DESede, Base64.decode(encryptKey));
        return aes.encryptBase64(input);
    }

    /**
     * DESede解密
     *
     * @param input      已加密字符串
     * @param decryptKey 密钥
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/2 16:14
     */
    public static String decrypt(String input, String decryptKey) {
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.DESede, Base64.decode(decryptKey));
        return aes.decryptStr(input);
    }

    /**
     * DESede随机生成密钥
     *
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/2 16:15
     */
    public static String generateKey() {
        //随机生成密钥
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.DESede.getValue()).getEncoded();
        return Base64.encode(key);
    }
}
