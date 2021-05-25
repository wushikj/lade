package com.wushi.lade.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * AES单元测试
 *
 * @author wushi
 * @date 2022/1/3 14:26
 */
class AESTest {

    /**
     * AES加密测试
     *
     * @return void
     * @author wushi
     * @date 2020/1/3 14:27
     */
    @Test
    void encrypt() {
        System.out.println("========开始【AES】加密测试！========");
        // 待加密内容
        String input = "test中文";
        // 密钥(不能随便写)，请使用【自动生成密钥】方法，生成相关密钥使用！！！
        String key = "J/f6eK265j5sXrpXhTODXw==";
        // 使用AES加密，预期结果：em+r7H1GkgeWPylvENc+JQ==
        String aesEncrypt = AES.encrypt(input, key);
        System.out.println("AES加密结果：" + aesEncrypt);
        // Junit单元测试，如果不相等会报错
        Assertions.assertEquals("em+r7H1GkgeWPylvENc+JQ==", aesEncrypt);
        System.out.println("========【AES】加密测试成功！========");
    }

    /**
     * AES解密测试
     *
     * @return void
     * @author wushi
     * @date 2020/1/3 15:13
     */
    @Test
    void decrypt() {
        System.out.println("========开始【AES】解密测试！========");
        // 密文
        String input = "em+r7H1GkgeWPylvENc+JQ==";
        // 密钥
        String key = "J/f6eK265j5sXrpXhTODXw==";
        // 使用AES解密，预期结果：test中文
        String result = AES.decrypt(input, key);
        System.out.println("AES解密结果：" + result);
        // Junit单元测试，如果不相等会报错
        Assertions.assertEquals("test中文", result);
        System.out.println("========【AES】解密测试成功！========");
    }

    /**
     * AES自动生成密钥测试
     *
     * @return void
     * @author wushi
     * @date 2020/1/3 15:13
     */
    @Test
    void generateKey() {
        String generateKey = AES.generateKey();
        // 例：J/f6eK265j5sXrpXhTODXw==
        System.out.println("AES自动生成密钥：" + generateKey);
    }
}