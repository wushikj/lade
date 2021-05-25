package com.wushi.lade.security;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

/**
 * AES算法
 *
 * @author wushi
 * @date 2020/1/2 14:59
 */
public class AES {

    /**
     * AES加密算法
     *
     * @param input      待加密的字符串
     * @param encryptKey 密钥
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/2 15:24
     */
    public static String encrypt(String input, String encryptKey) {
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, Base64.decode(encryptKey));
        return aes.encryptBase64(input);
    }

    /**
     * AES解密算法
     *
     * @param input      已加密字符串
     * @param decryptKey 密钥
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/2 15:27
     */
    public static String decrypt(String input, String decryptKey) {
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, Base64.decode(decryptKey));
        return aes.decryptStr(input);
    }

    /**
     * AES随机生成密钥
     *
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/2 15:28
     */
    public static String generateKey() {
        //随机生成密钥
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        return Base64.encode(key);
    }
}
