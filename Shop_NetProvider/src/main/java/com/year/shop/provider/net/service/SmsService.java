package com.year.shop.provider.net.service;

import com.fyy.mybabystudy.common.dto.sms.SmsCodeDto;
import com.fyy.mybabystudy.common.vo.R;

/**
 * @author fyy
 * @date 2019/12/30 0030 下午 16:44
 */
public interface SmsService {
    // 发送短信验证码 注册时
    R sendRCodeSms(String phone);

    // 发送短信验证码 忘记密码，密码找回
    R sendFCodeSms(String phone);

    // 发送提示短信
    R sendSms(String phone, String msg);

    // 发送短信验证码 注册时
    R checkCode(SmsCodeDto smsCodeDto);
}
