package com.year.shop.provider.net.core;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.year.shop.common.util.DateUtil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: CarSys
 * @description: 基于阿里云的OSS 资源的管理：上传、下载、删除、列表等操作
 * @author: Feri
 * @create: 2019-12-12 14:58
 */
public class AliOssUtil {
    // Endpoint以杭州为例，其它Region请按实际情况填写。
    private static String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    private static String accessKeyId = "LTAIhTvqTSmlmjeQ";
    private static String accessKeySecret = "X7X9w0Ck5GEIWgP9tl0Q6sgmFjQuMv";
    private static String bucketName = "qfjava1908";

    private static OSS client;

    static {
        client = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

    //上传
    public static String upload(String objName, byte[] arr) {
        //1、上传内容
        client.putObject(bucketName, objName, new ByteArrayInputStream(arr));
        //2、获取访问路径
        return client.generatePresignedUrl(bucketName, objName, DateUtil.addYear(3)).toString();
    }

    public static String upload(String objName, byte[] arr, Date expire) {
        //1、上传内容
        client.putObject(bucketName, objName, new ByteArrayInputStream(arr));
        //2、获取访问路径
        return client.generatePresignedUrl(bucketName, objName, expire).toString();
    }

    //上传
    public static String upload(String objName, String msg) {
        return upload(objName, msg.getBytes());
    }

    //读取
    public static String read(String objName) {
        // 调用ossClient.getObject返回一个OSSObject实例，该实例包含文件内容及文件元信息。
        OSSObject ossObject = client.getObject(bucketName, objName);
// 调用ossObject.getObjectContent获取文件输入流，可读取此输入流获取其内容。
        InputStream content = ossObject.getObjectContent();
        if (content != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            StringBuffer buffer = new StringBuffer();
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                content.close();
            } catch (Exception e) {
            }
            // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
            return buffer.toString();
        }
        return null;
    }

    //获取文件列表 返回对象名称
    public static List<String> list() {
        List<String> list = new ArrayList<>();
        // ossClient.listObjects返回ObjectListing实例，包含此次listObject请求的返回结果。
        ObjectListing objectListing = client.listObjects(bucketName);
// objectListing.getObjectSummaries获取所有文件的描述信息。
        for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            list.add(objectSummary.getKey());

        }
        return list;
    }

    //删除
    public static boolean del(String objName) {
        try {
            client.deleteObject(bucketName, objName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}