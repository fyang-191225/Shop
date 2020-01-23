package com.year.shop.entity.user;

import lombok.Data;

import java.util.Date;

/**
 * @author fyy
 * @date 2020/1/23 0023 下午 14:56
 */
@Data
public class UserDetail {

    private Integer id;
    private Integer uid; // 用户id
    private String nickname; // 昵称
    private String realname; // 真实姓名
    /**
     * 性别 1女 2男
     */
    private Integer sex;
    private Date birthday; // 生日
    private String address;  // 住址
    private String image; // 头像
    private Date created;
}
