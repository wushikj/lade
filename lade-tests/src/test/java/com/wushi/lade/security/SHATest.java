package com.wushi.lade.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * SHA单元测试
 * 注：摘要算法不可逆，所以无解密方法
 *
 * @author wushi
 * @date 2020/1/6 8:55
 */
class SHATest {

    /**
     * SHA1加密测试
     *
     * @return void
     * @author wushi
     * @date 2020/1/6 9:05
     */
    @Test
    void sha1Encrypt() {
        System.out.println("========开始【SHA1】加密测试！========");
        // 待加密内容
        String input = "test中文";
        // 使用SHA1加密，预期结果：ecabf586cef0d3b11c56549433ad50b81110a836
        String sha1Encrypt = SHA.sha1Encrypt(input);
        System.out.println("SHA1加密结果：" + sha1Encrypt);
        // Junit单元测试，如果不相等会报错
        Assertions.assertEquals("ecabf586cef0d3b11c56549433ad50b81110a836", sha1Encrypt);
        System.out.println("========【SHA1】加密测试成功！========");
    }

    /**
     * SHA256加密测试
     *
     * @return void
     * @author wushi
     * @date 2020/1/6 9:06
     */
    @Test
    void sha256Encrypt() {
        System.out.println("========开始【SHA256】加密测试！========");
        // 待加密内容
        String input = "test中文";
        // 使用SHA256加密，预期结果：8af7c7959618cc0900be74a52eee44aca05aeb72525864dc7ae25d31761beb65
        String sha256Encrypt = SHA.sha256Encrypt(input);
        System.out.println("SHA256加密结果：" + sha256Encrypt);
        // Junit单元测试，如果不相等会报错
        Assertions.assertEquals("8af7c7959618cc0900be74a52eee44aca05aeb72525864dc7ae25d31761beb65", sha256Encrypt);
        System.out.println("========【SHA256】加密测试成功！========");
    }

    /**
     * SHA384加密测试
     *
     * @return void
     * @author wushi
     * @date 2020/1/6 9:26
     */
    @Test
    void sha384Encrypt() {
        System.out.println("========开始【SHA384】加密测试！========");
        // 待加密内容
        String input = "test中文";
        // 使用SHA384加密，预期结果：219ece10ffe81c179511d4ffa43a0c75bbee737acdd18ee11839a1def78a4e10c94186ef8483fce85d0589bcbddf632e
        String sha384Encrypt = SHA.sha384Encrypt(input);
        System.out.println("SHA384加密结果：" + sha384Encrypt);
        // Junit单元测试，如果不相等会报错
        Assertions.assertEquals("219ece10ffe81c179511d4ffa43a0c75bbee737acdd18ee11839a1def78a4e10c94186ef8483fce85d0589bcbddf632e", sha384Encrypt);
        System.out.println("========【SHA384】加密测试成功！========");
    }

    /**
     * SHA512加密测试
     *
     * @return void
     * @author wushi
     * @date 2020/1/6 9:26
     */
    @Test
    void sha512Encrypt() {
        System.out.println("========开始【SHA512】加密测试！========");
        // 待加密内容
        String input = "test中文";
        // 使用SHA512加密，预期结果：ab43333a01571e767a2eb15460c270fa123964c121b88c73e274a211d6e2cf98b10d89220e8d04e2fc573354da93400f3255e94d445dc956e177aa4201a4fc30
        String sha512Encrypt = SHA.sha512Encrypt(input);
        System.out.println("SHA512加密结果：" + sha512Encrypt);
        // Junit单元测试，如果不相等会报错
        Assertions.assertEquals("ab43333a01571e767a2eb15460c270fa123964c121b88c73e274a211d6e2cf98b10d89220e8d04e2fc573354da93400f3255e94d445dc956e177aa4201a4fc30", sha512Encrypt);
        System.out.println("========【SHA512】加密测试成功！========");
    }
}