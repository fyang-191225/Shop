package com.year.shop.common.config;

import lombok.Data;

/**
 * 注册验证码的key  和忘记密码的key
 *
 * @author fyy
 * @date 2019/12/30 0030 下午 17:31
 */
@Data
public class RedisConfig {
    // 记录注册验证码的key
    public static final String RCODE_KEY = "sms:rcode:";
    // 记录忘记密码验证码的key
    public static final String FCODE_KEY = "sms:fcode:";

    // 记录登录相关内容
    public static final String TOKEN_KEY = "login:token:"; // 后面追加令牌 存储的值：用户的id
    // 记录用户对应的令牌
    public static final String USER_TOKEN = "login:user:"; // 后面追加uid List类型 值：令牌
    // 记录登录的次数  在线次数
    public static final String TOKENCOUNT_KEY = "login:uidtoken:"; // hash 类型 字段：login:uid 值 在线次数
    // 令牌有效期
    public static final int TOKEN_TIME = 1800; // 30分钟
    // 挤掉的令牌
    public static final String TOKEN_TKCK = "login:kick:"; // 后面追加uid  hash类型， 字段令牌， 值被挤掉的令牌
    // 冻结账户: 无论是手机号还是邮箱，失败超过3次密码不正确
    public static final String TOKEN_FROST = "login:frost:"; // 后面追加uid  list类型， 值：uid
    // 失败记录
    public static final String LOGIN_ERROR = "login:error:"; // 后面追加uid +：+时间戳

    //记录点赞信息的key  hash类型
    public static final String LIKE_HASH = "like:mybabystudy"; // 永久有效




    // 账号冻结时间 默认1小时
    public static final int TOKENFORST_TIME = 3600;// 60分钟


}
