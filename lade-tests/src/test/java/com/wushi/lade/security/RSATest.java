package com.wushi.lade.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * RSA单元测试
 *
 * @author wushi
 * @date 2020/1/3 16:39
 */
class RSATest {

    // RSA公钥
    private String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCsbUi8/DMzaxIEkAPqwdrI/F53\n" +
            "x/ZGBkcIzB6Diti8yswNjGjTs1OMFbH0VWGPmpnCFswgzsYWW6hEB1CT+NDg5bBw\n" +
            "UNnaCc2JoDQB7T+WrPzFBa7u2QhF0L7pNkZJ3INdxN+P/J77/q9kZUE4/IXWqpzH\n" +
            "AzTgaiCvXiIaER5oLwIDAQAB";

    // RSA私钥
    private String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKxtSLz8MzNrEgSQ\n" +
            "A+rB2sj8XnfH9kYGRwjMHoOK2LzKzA2MaNOzU4wVsfRVYY+amcIWzCDOxhZbqEQH\n" +
            "UJP40ODlsHBQ2doJzYmgNAHtP5as/MUFru7ZCEXQvuk2Rkncg13E34/8nvv+r2Rl\n" +
            "QTj8hdaqnMcDNOBqIK9eIhoRHmgvAgMBAAECgYAu2OpgXm1S1Q5qKu7Fg7H71YWd\n" +
            "KbkhwWbfXwYTW1ATy02xiKqaiihy8RoOdnwxViHcd/EyILv1KyYGha5A5eNqJQI8\n" +
            "fEq5nVBqVuYnzYn/yeNZbDw0KwqBD5cULUnKrBJWGromqDYf9bpU8PnEetgsxnsX\n" +
            "qsmhU93P+2In4Y28gQJBANlf5+hC0BpynC91kXhiVqOm/NykxIK9w28j1tFOWeeE\n" +
            "O0132nSvMPhI49I3kY9KXu7SCEJGoZVu00dJCFxJxSECQQDLEMNRkbEcuqfdXuhA\n" +
            "Y/H4UyzV1HmnBgsxcLD4KZi/PYATR16LO3F8bs15NMO9rXSXBF3hxzlf74o8tyZM\n" +
            "dTNPAkEA1/OjnObjf6Lk73FJd+OaVzMKetlmMMz9X2infYDtaEfB8pBjgxCZAdDI\n" +
            "JIv4G/ayoWgftFgfJynW1DcGC0lugQJAN/vDHXXhMl5zcSB9P2NOjnu/3KPbdiTA\n" +
            "aTz8TS6Kog6+hJIkipY0z21s0IGvhVeQ0SGvEFPwiXZs0hhcQq/9GQJBANRI5RHv\n" +
            "2KY8scHEFLN+uDi41rgO9xyQOh5lcV+3WTPxardqFW07jqhxv8z0yGfih8Ik40Hz\n" +
            "BQ8RglokM/X7eIk=";

    /**
     * RSA公钥加密、私钥解密
     *
     * @return void
     * @author wushi
     * @date 2020/1/3 16:39
     */
    @Test
    void publicEncrypt() {
        System.out.println("========开始【RSA公钥】加密测试！========");
        // 待加密内容
        String input = "test中文";
        System.out.println("待加密内容：" + input);
        // 使用RSA公钥加密
        String publicEncrypt = RSA.publicKeyEncrypt(input, publicKey);
        System.out.println("RSA公钥加密结果：" + publicEncrypt);
        System.out.println("========【RSA公钥】加密测试成功！========");

        System.out.println("========开始【RSA私钥】解密测试！========");
        // 使用RSA私钥解密，预期结果：test中文
        String privateDecrypt = RSA.privateKeyDecrypt(publicEncrypt, privateKey);
        System.out.println("RSA私钥解密结果：" + privateDecrypt);
        // Junit单元测试，如果不相等会报错
        Assertions.assertEquals("test中文", privateDecrypt);
        System.out.println("========【RSA私钥】解密测试成功！========");
    }

    /**
     * RSA公钥解密测试
     *
     * @return void
     * @author wushi
     * @date 2020/1/3 16:50
     */
    @Test
    void publicDecrypt() {

    }

    /**
     * RSA私钥加密、公钥解密
     *
     * @return void
     * @author wushi
     * @date 2020/1/3 16:50
     */
    @Test
    void privateEncrypt() {
        System.out.println("========开始【RSA私钥】加密测试！========");
        // 待加密内容
        String input = "test中文";
        System.out.println("待加密内容：" + input);
        // 使用RSA私钥加密
        String privateEncrypt = RSA.privateKeyEncrypt(input, privateKey);
        System.out.println("RSA私钥加密结果：" + privateEncrypt);
        // Junit单元测试，如果不相等会报错
        System.out.println("========【RSA私钥】加密测试成功！========");

        System.out.println("========开始【RSA公钥】解密测试！========");
        // 使用RSA公钥解密，预期结果：test中文
        String publicDecrypt = RSA.publicKeyDecrypt(privateEncrypt, publicKey);
        System.out.println("RSA公钥解密结果：" + publicDecrypt);
        // Junit单元测试，如果不相等会报错
        Assertions.assertEquals("test中文", publicDecrypt);
        System.out.println("========【RSA公钥】解密测试成功！========");
    }

    /**
     * RSA私钥解密测试
     *
     * @return void
     * @author wushi
     * @date 2020/1/3 16:56
     */
    @Test
    void privateDecrypt() {

    }

    /**
     * RSA自动生成公私钥测试
     *
     * @return void
     * @author wushi
     * @date 2020/1/3 17:19
     */
    @Test
    void generateKeyPair() {
        System.out.println("========【RSA】自动生成公私钥开始！========");
        Map<String, Object> result = RSA.generateKeyPair();
        String privateKey = result.get("privateKey").toString();
        String publicKey = result.get("publicKey").toString();
        System.out.println("RSA私钥：" + privateKey);
        System.out.println("RSA公钥：" + publicKey);
        System.out.println("========【RSA】自动生成公私钥成功！========");
    }
}