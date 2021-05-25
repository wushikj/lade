package com.wushi.lade.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * SM国密算法单元测试
 * 包含SM2-SM4
 *
 * @author wushi
 * @date 2020/1/6 9:27
 */
class SMTest {

    // SM2公钥
    private String publicKey = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEU0rVDsCPzrYOHeH5DvQ09" +
            "Ketvrwq9q7gOUcGaSq4czcBzO1ntgH6gqg7AV0b121SvZzA0QABZ3rcUubTfhGh1w==";
    // SM2私钥
    private String privateKey = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQ" +
            "Qgim1mEOM2PQNNTtXYcBdtYNoQ06TNMm/P3fOqH0Vlrv+gCgYIKoEcz1UBgi2hRANCAART" +
            "StUOwI/Otg4d4fkO9DT0p62+vCr2ruA5RwZpKrhzNwHM7We2AfqCqDsBXRvXbVK9nMDRAAFnetxS5tN+EaHX";

    /**
     * SM2公钥加密，私钥解密测试
     *
     * @return void
     * @author wushi
     * @date 2020/1/6 9:27
     */
    @Test
    void sm2PublicKeyEncrypt() {
        System.out.println("========开始【SM2公钥】加密测试！========");
        // 待加密内容
        String input = "test中文";
        System.out.println("待加密内容：" + input);
        // 使用SM2公钥加密
        String publicEncrypt = SM.sm2PublicKeyEncrypt(input, publicKey);
        System.out.println("SM2公钥加密结果：" + publicEncrypt);
        System.out.println("========【SM2公钥】加密测试成功！========");

        System.out.println("========开始【SM2私钥】解密测试！========");
        // 使用SM2私钥解密，预期结果：test中文
        String privateDecrypt = SM.sm2PrivateKeyDecrypt(publicEncrypt, privateKey);
        System.out.println("SM2私钥解密结果：" + privateDecrypt);
        // Junit单元测试，如果不相等会报错
        Assertions.assertEquals("test中文", privateDecrypt);
        System.out.println("========【SM2私钥】解密测试成功！========");
    }

    @Test
    void sm2PrivateKeyDecrypt() {
    }

    /**
     * SM2自动生成公私钥测试
     *
     * @return void
     * @author wushi
     * @date 2020/1/6 9:32
     */
    @Test
    void generateKeyPair() {
        Map<String, Object> generateKeyPair = SM.generateKeyPair();
        String privateKey = generateKeyPair.get("privateKey").toString();
        String publicKey = generateKeyPair.get("publicKey").toString();
        System.out.println("私钥：" + privateKey);
        System.out.println("公钥：" + publicKey);
    }

    /**
     * SM3摘要加密算法测试
     * 注：摘要加密不可逆
     *
     * @return void
     * @author wushi
     * @date 2020/1/6 11:12
     */
    @Test
    void sm3Encrypt() {
        System.out.println("========开始【SM3】加密测试！========");
        // 待加密内容
        String input = "test中文";
        // 使用SM3加密，预期结果：614a2eb8e9b14be901faf46155c40caa09d49cc61a1382447d28b98254c2234c
        String sm3Encrypt = SM.sm3Encrypt(input);
        System.out.println("SM3加密结果：" + sm3Encrypt);
        // Junit单元测试，如果不相等会报错
        Assertions.assertEquals("614a2eb8e9b14be901faf46155c40caa09d49cc61a1382447d28b98254c2234c", sm3Encrypt);
        System.out.println("========【SM3】加密测试成功！========");
    }

    /**
     * SM4加密测试【随机密钥生成】
     * 注：对称加密算法
     *
     * @return void
     * @author wushi
     * @date 2020/1/6 11:42
     */
    @Test
    void sm4EncryptRandom() {
        System.out.println("========开始【SM4】加密测试！========");
        // 待加密内容
        String input = "test中文";
        // 使用SM4加密
        String sm4Encrypt = SM.sm4Encrypt(input);
        System.out.println("SM4加密结果：" + sm4Encrypt);
        System.out.println("========【SM4】加密测试成功！========");

        System.out.println("========开始【SM4】解密测试！========");
        // 使用SM4解密，预期结果：test中文
        String sm4Decrypt = SM.sm4Decrypt(sm4Encrypt);
        System.out.println("SM4解密结果：" + sm4Decrypt);
        // Junit单元测试，如果不相等会报错
        Assertions.assertEquals("test中文", sm4Decrypt);
        System.out.println("========【SM4】解密测试成功！========");
    }

    /**
     * SM4加密测试
     * 注：对称加密算法
     *
     * @return void
     * @author wushi
     * @date 2020/1/6 11:42
     */
    @Test
    void sm4Encrypt() {
        System.out.println("========开始【SM4】加密测试！========");
        // 密钥
        String keyStr = "6JfMOew5YUBXJ2KDJSl+Jw==";
        // 待加密内容
        String input = "test中文";
        // 使用SM4加密
        String sm4Encrypt = SM.sm4Encrypt(input, keyStr);
        System.out.println("SM4加密结果：" + sm4Encrypt);
        System.out.println("========【SM4】加密测试成功！========");

    }

    /**
     * SM4解密测试
     * 注：对称加密算法
     *
     * @return void
     * @author wushi
     * @date 2020/1/6 11:43
     */
    @Test
    void sm4Decrypt() {
        System.out.println("========开始【SM4】解密测试！========");
        // 密钥
        String keyStr = "6JfMOew5YUBXJ2KDJSl+Jw==";
        // 密文
        String sm4Encrypt = "5c02abe9cfa6965a941921d3ce20445d";
        // 使用SM4解密，预期结果：test中文
        String sm4Decrypt = SM.sm4Decrypt(sm4Encrypt, keyStr);
        System.out.println("SM4解密结果：" + sm4Decrypt);
        // Junit单元测试，如果不相等会报错
        Assertions.assertEquals("test中文", sm4Decrypt);
        System.out.println("========【SM4】解密测试成功！========");
    }

    /**
     * SM4自动生成密钥测试
     *
     * @return void
     * @author wushi
     * @date 2020/1/6 16:59
     */
    @Test
    void generateKey() {
        String generateKey = SM.generateKey();
        System.out.println("SM4密钥：" + generateKey);
    }
}