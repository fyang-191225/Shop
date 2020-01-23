package com.year.shop.common.util;

import java.util.Random;

/**
 * 生成验证码
 *
 * @author fyy
 * @date 2019/12/30 0030 下午 17:56
 */
public class RandomUtil {
    public static int creatNum(int len) {
        Random random = new Random();
        int max = (int) (Math.pow(10, len) - Math.pow(10, len - 1));
        return random.nextInt(max)+(int)(Math.pow(10,len-1));
    }
}
