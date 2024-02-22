package com.mobaijun.util;

import java.util.Random;

/**
 * Description: [随机工具类]
 * Author: [mobaijun]
 * Date: [2024/2/22 11:36]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class RandomUtil {

    /**
     * 生成指定范围内的随机整数
     *
     * @param min 最小值（包含）
     * @param max 最大值（包含）
     * @return 在指定范围内的随机整数
     */
    public static int next(int min, int max) {
        Random random = new Random();
        // 使用 nextInt 方法生成 [0, max - min + 1) 范围内的随机数，然后加上 min
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * 生成指定范围内的随机长整数
     *
     * @param min 最小值（包含）
     * @param max 最大值（包含）
     * @return 在指定范围内的随机长整数
     */
    public static long next(long min, long max) {
        Random random = new Random();
        // 使用 nextLong 方法生成 [0, max - min + 1) 范围内的随机数，然后加上 min
        return random.nextLong(max - min + 1) + min;
    }
}
