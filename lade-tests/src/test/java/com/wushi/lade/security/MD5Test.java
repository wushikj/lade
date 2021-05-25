package com.wushi.lade.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * MD5单元测试
 * 注：摘要算法不可逆，所以无解密方法
 *
 * @author wushi
 * @date 2020/1/3 9:55
 */
class MD5Test {

    /**
     * MD5加密测试【返回32位】
     *
     * @return void
     * @author wushi
     * @date 2020/1/3 9:55
     */
    @Test
    void md5Encrypt() {
        System.out.println("========开始【MD5】32位加密测试！========");
        // 待加密内容
        String input = "test中文";
        // 使用MD5加密，预期结果：5393554e94bf0eb6436f240a4fd71282
        String md5Encrypt = MD5.md5Encrypt(input);
        System.out.println("MD5加密32位结果：" + md5Encrypt);
        // Junit单元测试，如果不相等会报错
        Assertions.assertEquals("5393554e94bf0eb6436f240a4fd71282", md5Encrypt);
        System.out.println("========【MD5】32位加密测试成功！========");
    }

    /**
     * MD5加密测试【返回16位】
     *
     * @return void
     * @author wushi
     * @date 2020/1/3 9:59
     */
    @Test
    void md5EncryptHex16() {
        System.out.println("========开始【MD5】16位加密测试！========");
        // 待加密内容
        String input = "test中文";
        // 使用MD5加密，预期结果：94bf0eb6436f240a
        String md5Encrypt = MD5.md5Encrypt(input, true);
        System.out.println("MD5加密16位结果：" + md5Encrypt);
        // Junit单元测试，如果不相等会报错
        Assertions.assertEquals("94bf0eb6436f240a", md5Encrypt);
        System.out.println("========【MD5】16位加密测试成功！========");
    }
}