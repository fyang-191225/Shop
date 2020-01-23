package com.year.shop.common.util;

import java.util.Random;

/**
 * @author fyy
 * @date 2019/12/30 0030 下午 16:38
 */
public class T {
    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int i1 = random.nextInt(3)+1;
            System.out.println(random.nextInt(3)+1);
        }

    }
}
