package com.year.shop.provider.net.core;

import java.util.UUID;

/**
 * @author fyy
 * @date 2020/1/2 0002 下午 19:50
 */
public class FileUtil {
    /**
     * 文件名的重命名
     * @param fn
     * @return
     */
    public static String renameFile(String fn) {
        if (fn.length() > 50) {
            fn = fn.substring(fn.length() - 50);
        }
        return System.currentTimeMillis()+ "_"+ UUID.randomUUID().toString().replace("_","") +"_" +fn;
    }
}
