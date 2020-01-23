package com.year.shop.provider.net.core;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

/**
 * @program: CarSys
 * @description:
 * @author: Feri
 * @create: 2019-12-11 11:41
 */
public class AliSmsUtil {

    /**
     * 发送短信验证码  注册 有效期10分钟
     * @param phone 手机号
     * @param code 验证码 6位*/
    public static boolean sendSmsCode(String phone,int code){
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou","LTAIhTvqTSmlmjeQ", "X7X9w0Ck5GEIWgP9tl0Q6sgmFjQuMv");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "来自邢朋辉的短信");
        request.putQueryParameter("TemplateCode", "SMS_115250125");
        request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //发送密码 找回验证码
    public static  void sendCode(String phone,int code){
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou","LTAIhTvqTSmlmjeQ", "X7X9w0Ck5GEIWgP9tl0Q6sgmFjQuMv");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "来自邢朋辉的短信");
        request.putQueryParameter("TemplateCode", "SMS_181545700");
        request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
