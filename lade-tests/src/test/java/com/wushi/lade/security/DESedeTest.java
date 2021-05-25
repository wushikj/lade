package com.wushi.lade.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * DESede单元测试
 *
 * @author wushi
 * @date 2020/1/3 15:36
 */
class DESedeTest {

    /**
     * DESede加密测试
     *
     * @return void
     * @author wushi
     * @date 2020/1/3 15:25
     */
    @Test
    void encrypt() {
        System.out.println("========开始【DESede】加密测试！========");
        // 待加密内容
        String input = "test中文";
        // 密钥(不能随便写)，请使用【自动生成密钥】方法，生成相关密钥使用！！！
        String key = "ndOuhv5bGcG2WP2/sD6krp3Trob+WxnB";
        // 使用DESede加密，预期结果：7tgmMAdfJst/ejFw71STRA==
        String desEncrypt = DESede.encrypt(input, key);
        System.out.println("DESede加密结果：" + desEncrypt);
        // Junit单元测试，如果不相等会报错
        Assertions.assertEquals("7tgmMAdfJst/ejFw71STRA==", desEncrypt);
        System.out.println("========【DESede】加密测试成功！========");
    }

    /**
     * DES解密测试
     *
     * @return void
     * @author wushi
     * @date 2020/1/3 15:37
     */
    @Test
    void decrypt() {
        System.out.println("========开始【DESede】解密测试！========");
        // 密文
        String input = "7tgmMAdfJst/ejFw71STRA==";
        // 密钥
        String key = "ndOuhv5bGcG2WP2/sD6krp3Trob+WxnB";
        // 使用DESede解密，预期结果：test中文
        String result = DESede.decrypt(input, key);
        System.out.println("DESede解密结果：" + result);
        // Junit单元测试，如果不相等会报错
        Assertions.assertEquals("test中文", result);
        System.out.println("========【DESede】解密测试成功！========");
    }

    /**
     * DESede自动生成密钥测试
     *
     * @return void
     * @author wushi
     * @date 2020/1/3 15:38
     */
    @Test
    void generateKey() {
        String generateKey = DESede.generateKey();
        // 例：ndOuhv5bGcG2WP2/sD6krp3Trob+WxnB
        System.out.println("DESede自动生成密钥：" + generateKey);
    }
}