package com.mobaijun.util;

/**
 * Description: [16进制工具类]
 * Author: [mobaijun]
 * Date: [2024/2/22 11:15]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class Hex {

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param src 要转换的字节数组
     * @return 转换后的十六进制字符串
     */
    public static String bytesToHexString(byte[] src) {
        if (src == null || src.length == 0) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : src) {
            // 使用 String.format 将字节转换为两位十六进制字符串，并追加到 StringBuilder 中
            stringBuilder.append(String.format("%02X", b));
        }

        return stringBuilder.toString();
    }

    /**
     * 将十六进制字符串转换为字节数组
     *
     * @param hexString 要转换的十六进制字符串
     * @return 转换后的字节数组
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.isEmpty()) {
            return null;
        }

        // 将输入的十六进制字符串转换为大写形式
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        byte[] d = new byte[length];
        char[] hexChars = hexString.toCharArray();

        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            // 将两个十六进制字符转换为一个字节
            d[i] = (byte) ((charToByte(hexChars[pos]) << 4) | charToByte(hexChars[pos + 1]));
        }

        return d;
    }

    /**
     * 将十六进制字符转换为字节
     *
     * @param c 十六进制字符
     * @return 字节
     */
    private static byte charToByte(char c) {
        // 在 "0123456789ABCDEF" 字符串中查找对应字符的位置，并返回其索引（即字节值）
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
