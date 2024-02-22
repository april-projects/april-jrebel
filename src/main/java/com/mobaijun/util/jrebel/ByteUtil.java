package com.mobaijun.util.jrebel;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;

/**
 * Description: [字节工具类]
 * Author: [mobaijun]
 * Date: [2024/2/22 11:27]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class ByteUtil {

    /**
     * 使用线程安全的 Random 实例
     */
    private static final Random RANDOM_GENERATOR;

    // 静态初始化块，创建 Random 实例
    static {
        RANDOM_GENERATOR = new Random();
    }

    /**
     * 将字节数组编码为 Base64 字符串
     *
     * @param binaryData 待编码的字节数组
     * @return Base64 编码后的字符串，如果输入为空则返回 null
     */
    public static String encodeBase64(final byte[] binaryData) {
        if (binaryData == null) {
            return null;
        }
        // 使用 UTF-8 字符集编码字节数组，并进行 Base64 编码
        return new String(Base64.getEncoder().encode(binaryData), StandardCharsets.UTF_8);
    }

    /**
     * 将 Base64 字符串解码为字节数组
     *
     * @param s 待解码的 Base64 字符串
     * @return 解码后的字节数组，如果输入为空则返回 null
     */
    public static byte[] decodeBase64(final String s) {
        if (s == null) {
            return null;
        }
        // 对 Base64 字符串进行解码，并使用 UTF-8 字符集转换为字节数组
        return Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成指定长度的随机字节数组
     *
     * @param length 随机字节数组的长度
     * @return 指定长度的随机字节数组
     */
    public static byte[] generateRandomBytes(final int length) {
        final byte[] array = new byte[length];
        // 使用静态的 Random 实例生成随机字节数组
        RANDOM_GENERATOR.nextBytes(array);
        return array;
    }
}
