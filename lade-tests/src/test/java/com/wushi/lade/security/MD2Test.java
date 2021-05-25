package com.wushi.lade.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * MD2单元测试
 * 注：摘要算法不可逆，所以无解密方法
 *
 * @author wushi
 * @date 2020/1/3 9:46
 */
class MD2Test {

    /**
     * MD2加密测试
     *
     * @return void
     * @author wushi
     * @date 2020/1/3 9:46
     */
    @Test
    void md2Encrypt() {
        System.out.println("========开始【MD2】加密测试！========");
        // 待加密内容
        String input = "test中文";
        // 使用MD2加密，预期结果：b337fd6701d23bb02d077d88a4e306cf
        String md2Encrypt = MD2.md2Encrypt(input);
        System.out.println("MD2加密结果：" + md2Encrypt);
        // Junit单元测试，如果不相等会报错
        Assertions.assertEquals("b337fd6701d23bb02d077d88a4e306cf", md2Encrypt);
        System.out.println("========【MD2】加密测试成功！========");
    }
}