package com.wushi.lade.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * HMAC单元测试
 *
 * @author wushi
 * @date 2020/1/3 15:57
 */
class HMACTest {

    /**
     * HmacMD5测试
     *
     * @return void
     * @author wushi
     * @date 2020/1/3 15:58
     */
    @Test
    void hmacMd5() {
        System.out.println("========开始【HmacMD5】加密测试！========");
        // 待加密内容
        String input = "test中文";
        // 密钥
        String key = "password";
        // 使用HmacMD5加密，预期结果：b977f4b13f93f549e06140971bded384
        String hmacMd5 = HMAC.hmacMd5(input, key);
        System.out.println("HmacMD5加密结果：" + hmacMd5);
        // Junit单元测试，如果不相等会报错
        Assertions.assertEquals("b977f4b13f93f549e06140971bded384", hmacMd5);
        System.out.println("========【HmacMD5】加密测试成功！========");
    }

    /**
     * HmacSha1测试
     *
     * @return void
     * @author wushi
     * @date 2020/1/3 16:11
     */
    @Test
    void hmacSha1() {
        System.out.println("========开始【HmacSha1】加密测试！========");
        // 待加密内容
        String input = "test中文";
        // 密钥
        String key = "password";
        // 使用HmacSha1加密，预期结果：1dd68d2f119d5640f0d416e99d3f42408b88d511
        String hmacSha1 = HMAC.hmacSha1(input, key);
        System.out.println("HmacSha1加密结果：" + hmacSha1);
        // Junit单元测试，如果不相等会报错
        Assertions.assertEquals("1dd68d2f119d5640f0d416e99d3f42408b88d511", hmacSha1);
        System.out.println("========【HmacSha1】加密测试成功！========");
    }

    /**
     * HmacSha256测试
     *
     * @return void
     * @author wushi
     * @date 2020/1/3 16:13
     */
    @Test
    void hmacSha256() {
        System.out.println("========开始【HmacSha256】加密测试！========");
        // 待加密内容
        String input = "test中文";
        // 密钥
        String key = "password";
        // 使用HmacSha256加密，预期结果：f6642edbb1220b8ca174df37bcb393665f95b754aaa48a4d881d67400067c2f1
        String hmacSha256 = HMAC.hmacSha256(input, key);
        System.out.println("HmacSha256加密结果：" + hmacSha256);
        // Junit单元测试，如果不相等会报错
        Assertions.assertEquals("f6642edbb1220b8ca174df37bcb393665f95b754aaa48a4d881d67400067c2f1", hmacSha256);
        System.out.println("========【HmacSha256】加密测试成功！========");
    }

    /**
     * HmacSha384测试
     *
     * @return void
     * @author wushi
     * @date 2020/1/3 16:13
     */
    @Test
    void hmacSha384() {
        System.out.println("========开始【HmacSha384】加密测试！========");
        // 待加密内容
        String input = "test中文";
        // 密钥
        String key = "password";
        // 使用HmacSha384加密，预期结果：b56f2dad59b9b9ad7e0058573c5b0fa12cf34c3083f934286140a511c121eb9c1ba1c1f23d36c1c346d70145806cc6cf
        String hmacSha384 = HMAC.hmacSha384(input, key);
        System.out.println("HmacSha384加密结果：" + hmacSha384);
        // Junit单元测试，如果不相等会报错
        Assertions.assertEquals("b56f2dad59b9b9ad7e0058573c5b0fa12cf34c3083f934286140a511c121eb9c1ba1c1f23d36c1c346d70145806cc6cf", hmacSha384);
        System.out.println("========【HmacSha384】加密测试成功！========");
    }

    /**
     * HmacSha512测试
     *
     * @return void
     * @author wushi
     * @date 2020/1/3 16:20
     */
    @Test
    void hmacSha512() {
        System.out.println("========开始【HmacSha512】加密测试！========");
        // 待加密内容
        String input = "test中文";
        // 密钥
        String key = "password";
        // 使用HmacSha512加密，预期结果：b8da38141632ba4e8a66fa1d6707276f509b82e4d3806ce887e7588f8983c34b9f1c5c25f2515aa8b07613e9809f9c75ae54f226d50534d51d5cf26ed0d3db99
        String hmacSha512 = HMAC.hmacSha512(input, key);
        System.out.println("HmacSha512加密结果：" + hmacSha512);
        // Junit单元测试，如果不相等会报错
        Assertions.assertEquals("b8da38141632ba4e8a66fa1d6707276f509b82e4d3806ce887e7588f8983c34b9f1c5c25f2515aa8b07613e9809f9c75ae54f226d50534d51d5cf26ed0d3db99", hmacSha512);
        System.out.println("========【HmacSha512】加密测试成功！========");
    }
}