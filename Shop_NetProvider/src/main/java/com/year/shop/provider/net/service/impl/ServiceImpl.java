package com.year.shop.provider.net.service.impl;

import com.fyy.mybabystudy.common.config.RedisConfig;
import com.fyy.mybabystudy.common.config.SystemConfig;
import com.fyy.mybabystudy.common.dto.sms.SmsCodeDto;
import com.fyy.mybabystudy.common.util.JedisUtil;
import com.fyy.mybabystudy.common.util.RandomUtil;
import com.fyy.mybabystudy.common.util.StrUtil;
import com.fyy.mybabystudy.common.vo.R;
import com.fyy.mybabystudy.entity.net.Sms;
import com.fyy.mybabystudy.provider.net.core.AliSmsUtil;
import com.fyy.mybabystudy.provider.net.dao.SmsDao;
import com.fyy.mybabystudy.provider.net.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fyy
 * @date 2019/12/30 0030 下午 16:46
 */
@Service
public class ServiceImpl implements SmsService {

    @Autowired
    private SmsDao smsDao;

    @Autowired
    private JedisUtil jedisUtil;

    /**
     * 发送短信验证码  注册
     *
     * @param phone
     * @return
     */
    @Override
    public R sendRCodeSms(String phone) {
        return sendCode(phone, 1);
    }

    /**
     * 发送短信验证码  忘记密码时
     *
     * @param phone
     * @return
     */
    @Override
    public R sendFCodeSms(String phone) {
        return sendCode(phone,2);
    }

    /**
     * 发送提示短信
     *
     * @param phone 手机号
     * @param msg   短信的内容
     * @return
     */
    @Override
    public R sendSms(String phone, String msg) {
        return null;
    }

    /**
     * 短信校验 type==1 注册的短信验证 type==2  找回密码的短信验证
     *
     * @param smsCodeDto
     * @return
     */
    @Override
    public R checkCode(SmsCodeDto smsCodeDto) {
        switch (smsCodeDto.getCode()) {
            case 1: // 注册的短信验证
                if (jedisUtil.checkKey(RedisConfig.RCODE_KEY + smsCodeDto.getPhone())) {
                    // 用从前端获取的code 验证码  和通过key 去redis中取对应的验证码值 比较，
                    if (smsCodeDto.getCode() == Integer.parseInt(jedisUtil.getStr(RedisConfig.RCODE_KEY + smsCodeDto.getPhone()))) {
                        return R.ok();
                    }
                }
                break;
            case 2: // 找回密码的短信验证
                if (jedisUtil.checkKey(RedisConfig.FCODE_KEY + smsCodeDto.getPhone())) {
                    if (smsCodeDto.getCode() == Integer.parseInt(jedisUtil.getStr(RedisConfig.FCODE_KEY + smsCodeDto.getPhone()))) {
                        return R.ok();
                    }
                }
                break;
        }
        return R.fail("验证码失效或不一致");
    }

    // 发送验证码的短信 封装
    private R sendCode(String phone, int type) {
        if (!StrUtil.isEmpty(phone) && phone.matches("1[3-9][0-9]{8}")) {
            String key = "";
            switch (type) {
                case 1: // 发送验证码 注册
                    key = RedisConfig.RCODE_KEY;
                    break;
                case 2: // 发送验证码 忘记密码
                    key = RedisConfig.FCODE_KEY;
                    break;
            }
            // 获取code值
            int code;
            boolean ishave = false; // 失效状态
            if (jedisUtil.checkKey(key + phone)) { // 检验key 是否有效 true 表示有效；
                ishave = true; // 有效， 改变状态
                // 有效 就取对应key的code值
                code = Integer.parseInt(jedisUtil.getStr(RedisConfig.RCODE_KEY + phone));
            } else { // 否则 false  就表示无效
                // 失效 就创建验证码
                code = RandomUtil.creatNum(6);
            }
            // 调用手机短信接口 往手机发送验证码短信
            if (type == 1) {
                // type == 1 发送有验证码 注册时
                AliSmsUtil.sendSmsCode(phone,code);
            } else if (type == 2) {
                // type == 2 发送有验证码 忘记密码时
                AliSmsUtil.sendCode(phone,code);
            }
            // 存储验证码到redis中
            if (!ishave) { // 无效时存在redis 中
                jedisUtil.addStr(key+phone,code+"", SystemConfig.CODE_TIME);
            }
            // 添加到数据库
            Sms sms = new Sms();
            sms.setContent("发送短信验证码：" + code); // 设置内容
            sms.setPhone(phone); // 手机号
            sms.setTempname("来自冯洋洋的短信");
            sms.setType(type); // 类型
            smsDao.insert(sms);
            return R.ok();
        } else {
            return R.fail("亲，手机号不能为空");
        }
    }
}
