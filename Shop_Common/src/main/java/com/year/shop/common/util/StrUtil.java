package com.year.shop.common.util;

/**
 * 字符串是否为空
 *
 * @author fyy
 * @date 2019/12/30 0030 下午 19:14
 */
public class StrUtil {
    /**
     * 校验字符串是否为空(null 或长度=0)
     * */
    public static boolean isEmpty(String... strings ) {
        for (String s : strings) {
            if (s == null && s.length() == 0) {
                return true;
            }
        }
        return false;
    }
}
