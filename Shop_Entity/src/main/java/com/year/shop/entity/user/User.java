package com.year.shop.entity.user;

import lombok.Data;

import java.sql.Date;

/**
 * @author fyy
 * @date 2020/1/22 0022 上午 10:57
 */
@Data
public class User {

    private Integer id;

    private String username;

    private String password;

    private String phone;

    private String email;

    private Date created;

    private Date updated;
}
