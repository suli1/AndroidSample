package com.suli.lib.utils;

import java.util.Random;

/**
 * Created by suli690 on 2017/4/8.
 * 生产一些随机数，随机字符串的工具类
 */

public class RandomUtils {

    private RandomUtils() {
        throw new UnsupportedOperationException("you can't instantiate me...");
    }

    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
