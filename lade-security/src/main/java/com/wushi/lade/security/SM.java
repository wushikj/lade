package com.wushi.lade.security;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

/**
 * SM国密算法
 * 注：包含SM2-SM4(非对称,摘要,对称)
 * 其中SM4算法加密解密不能在不同线程中使用，否则无法解密
 *
 * @author wushi
 * @date 2020/1/3 0:21
 */
public class SM {

    /**
     * 实例化SM4类
     */
    private static SymmetricCrypto sm4 = SmUtil.sm4();

    /**
     * SM2 公钥加密
     * 注：非对称加密，公钥加密，私钥解密
     * 不支持：私钥加密，公钥解密！！！
     *
     * @param input     待加密字符串
     * @param publicKey SM2公钥
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/3 0:26
     */
    public static String sm2PublicKeyEncrypt(String input, String publicKey) {
        return SmUtil.sm2(null, publicKey).encryptBcd(input, KeyType.PublicKey);
    }

    /**
     * SM2 私钥解密
     * 注：非对称加密，公钥加密，私钥解密
     * 不支持：私钥加密，公钥解密！！！
     *
     * @param encryptStr 已加密字符串
     * @param privateKey SM2私钥
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/3 0:28
     */
    public static String sm2PrivateKeyDecrypt(String encryptStr, String privateKey) {
        return SmUtil.sm2(privateKey, null).decryptStr(encryptStr, KeyType.PrivateKey);
    }

    /**
     * SM2自动生成【公私钥】
     * 返回值为“privateKey”与“publicKey”
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author wushi
     * @date 2020/1/6 9:30
     */
    public static Map<String, Object> generateKeyPair() {
        KeyPair pair = SecureUtil.generateKeyPair("SM2");
        String privateKey = Base64.encode(pair.getPrivate().getEncoded());
        String publicKey = Base64.encode(pair.getPublic().getEncoded());
        Map<String, Object> result = new HashMap<>(4);
        result.put("privateKey", privateKey);
        result.put("publicKey", publicKey);
        return result;
    }

    /**
     * SM3加密
     * 注：摘要算法，不可逆
     *
     * @param input 待加密字符串
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/3 0:32
     */
    public static String sm3Encrypt(String input) {
        return SmUtil.sm3(input);
    }

    /**
     * SM4加密【随机生成密钥】
     * 注：对称加密
     *
     * @param input 待加密字符串
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/3 0:35
     */
    public static String sm4Encrypt(String input) {
        return sm4.encryptHex(input);
    }

    /**
     * SM4解密【随机生成密钥】
     * 注：对称加密
     *
     * @param encrypt 已加密字符串
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/3 0:36
     */
    public static String sm4Decrypt(String encrypt) {
        return sm4.decryptStr(encrypt, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * SM4加密
     * 注：对称加密
     *
     * @param input 待加密字符串
     * @param key   16位密钥
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/3 0:35
     */
    public static String sm4Encrypt(String input, String key) {
        return SmUtil.sm4(Base64.decode(key)).encryptHex(input);
    }

    /**
     * SM4解密
     * 注：对称加密
     *
     * @param encrypt 已加密字符串
     * @param key     16位密钥
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/3 0:36
     */
    public static String sm4Decrypt(String encrypt, String key) {
        return SmUtil.sm4(Base64.decode(key)).decryptStr(encrypt, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * SM4自动生成密钥
     *
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/6 16:58
     */
    public static String generateKey() {
        //随机生成密钥
        byte[] key = SecureUtil.generateKey("SM4").getEncoded();
        return Base64.encode(key);
    }
}
