package com.year.shop.common.dto;

import lombok.Data;

/**
 * 令牌需要的信息
 * 令牌对象
 * @author fyy
 * @date 2019/12/30 0030 下午 17:08
 */
@Data
public class TokenDto {
    private int uid;
    private int type; // 类型 1。 手机号 2 邮箱
    private String phone;
    private String email;

    public TokenDto() {
    }

    public TokenDto(int uid, int type, String phone, String email) {
        this.uid = uid;
        this.type = type;
        this.phone = phone;
        this.email = email;
    }
}
