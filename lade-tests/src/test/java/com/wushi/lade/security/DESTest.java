package com.wushi.lade.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * DES单元测试
 *
 * @author wushi
 * @date 2020/1/3 15:25
 */
class DESTest {

    /**
     * DES加密测试
     *
     * @return void
     * @author wushi
     * @date 2020/1/3 15:25
     */
    @Test
    void encrypt() {
        System.out.println("========开始【DES】加密测试！========");
        // 待加密内容
        String input = "test中文";
        // 密钥(不能随便写)，请使用【自动生成密钥】方法，生成相关密钥使用！！！
        String key = "Ydkjc9oQbXo=";
        // 使用DES加密，预期结果：hfJfLKi37xuWZDMTvwO0aA==
        String encrypt = DES.encrypt(input, key);
        System.out.println("DES加密结果：" + encrypt);
        // Junit单元测试，如果不相等会报错
        Assertions.assertEquals("hfJfLKi37xuWZDMTvwO0aA==", encrypt);
        System.out.println("========【DES】加密测试成功！========");
    }

    /**
     * DES解密测试
     *
     * @return void
     * @author wushi
     * @date 2020/1/3 15:31
     */
    @Test
    void decrypt() {
        System.out.println("========开始【DES】解密测试！========");
        // 密文
        String input = "hfJfLKi37xuWZDMTvwO0aA==";
        // 密钥
        String key = "Ydkjc9oQbXo=";
        // 使用DES解密，预期结果：test中文
        String decrypt = DES.decrypt(input, key);
        System.out.println("DES解密结果：" + decrypt);
        // Junit单元测试，如果不相等会报错
        Assertions.assertEquals("test中文", decrypt);
        System.out.println("========【DES】解密测试成功！========");
    }

    /**
     * DES自动生成密钥测试
     *
     * @return void
     * @author wushi
     * @date 2020/1/3 15:29
     */
    @Test
    void generateKey() {
        String generateKey = DES.generateKey();
        // 例：Ydkjc9oQbXo=
        System.out.println("DES自动生成密钥：" + generateKey);
    }
}