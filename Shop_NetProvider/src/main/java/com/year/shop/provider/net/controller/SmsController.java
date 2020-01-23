package com.year.shop.provider.net.controller;

import com.fyy.mybabystudy.common.dto.sms.SmsCodeDto;
import com.fyy.mybabystudy.common.vo.R;
import com.fyy.mybabystudy.provider.net.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fyy
 * @date 2019/12/30 0030 下午 16:45
 */
@RestController
public class SmsController {

    @Autowired
    private SmsService smsService;

    /**
     * 发送验证码 注册
     * @param phone
     * @return
     */
    @PostMapping("/provider/sms/sendrcode/{0}")
    public R sendCode1(@PathVariable String phone) {
        return smsService.sendRCodeSms(phone);
    }

    /**
     * 发送验证码 忘记密码
     * @param phone
     * @return
     */
    @PostMapping("/provider/sms/sendfcode/{0}")
    public R sendCode2(@PathVariable String phone) {
        return smsService.sendFCodeSms(phone);
    }

    /**
     * 校验
     * @param smsCodeDto
     * @return
     */
    @PostMapping("/provider/sms/checkcode")
    public R check(@RequestBody SmsCodeDto smsCodeDto) {
        return smsService.checkCode(smsCodeDto);
    }

}
