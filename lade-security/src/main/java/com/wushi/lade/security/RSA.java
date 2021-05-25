package com.wushi.lade.security;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;

import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA算法
 *
 * @author wushi
 * @date 2020/1/2 17:24
 */
public class RSA {

    /**
     * RSA公钥加密
     *
     * @param input        待加密字符串
     * @param publicKeyStr RSA公钥
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/2 17:29
     */
    public static String publicKeyEncrypt(String input, String publicKeyStr) {
        cn.hutool.crypto.asymmetric.RSA rsa = new cn.hutool.crypto.asymmetric.RSA(null, publicKeyStr);
        return rsa.encryptBcd(input, KeyType.PublicKey);
    }

    /**
     * RSA公钥解密
     *
     * @param input        已加密字符串
     * @param publicKeyStr RSA公钥
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/2 17:30
     */
    public static String publicKeyDecrypt(String input, String publicKeyStr) {
        cn.hutool.crypto.asymmetric.RSA rsa = new cn.hutool.crypto.asymmetric.RSA(null, publicKeyStr);
        return rsa.decryptStr(input, KeyType.PublicKey);
    }

    /**
     * RSA私钥加密
     *
     * @param input         待加密字符串
     * @param privateKeyStr RSA私钥
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/2 17:32
     */
    public static String privateKeyEncrypt(String input, String privateKeyStr) {
        cn.hutool.crypto.asymmetric.RSA rsa = new cn.hutool.crypto.asymmetric.RSA(privateKeyStr, null);
        return rsa.encryptBcd(input, KeyType.PrivateKey);
    }

    /**
     * RSA私钥解密
     *
     * @param input         已加密字符串
     * @param privateKeyStr 私钥
     * @return java.lang.String
     * @author wushi
     * @date 2020/1/2 17:32
     */
    public static String privateKeyDecrypt(String input, String privateKeyStr) {
        cn.hutool.crypto.asymmetric.RSA rsa = new cn.hutool.crypto.asymmetric.RSA(privateKeyStr, null);
        return rsa.decryptStr(input, KeyType.PrivateKey);
    }

    /**
     * RSA自动生成【公私钥】
     * 返回值为“privateKey”与“publicKey”
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author wushi
     * @date 2020/1/2 23:30
     */
    public static Map<String, Object> generateKeyPair() {
        KeyPair pair = SecureUtil.generateKeyPair("RSA");
        String privateKey = Base64.encode(pair.getPrivate().getEncoded());
        String publicKey = Base64.encode(pair.getPublic().getEncoded());
        Map<String, Object> result = new HashMap<>(4);
        result.put("privateKey", privateKey);
        result.put("publicKey", publicKey);
        return result;
    }

}
