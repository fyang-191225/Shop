package com.year.shop.common.dto.user;

import lombok.Data;

/**
 * @author fyy
 * @date 2020/1/22 0022 上午 11:01
 */
@Data
public class UserLoginDto {
    private int type; // 1 手机号登录，2 邮箱登录
    private String name; // 账号， 手机号， 邮箱
    private String pass; // 密码
    private String deviceName; // 设备名称 pc Android iphone ios
    private String model; // 型号
    private String address; // 地址
}
