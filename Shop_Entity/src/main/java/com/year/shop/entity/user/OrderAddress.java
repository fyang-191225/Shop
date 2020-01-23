package com.year.shop.entity.user;

import lombok.Data;

import java.sql.Date;

/**
 * 订单收货地址信息
 *
 * @author fyy
 * @date 2020/1/23 0023 下午 12:44
 */
@Data
public class OrderAddress {
    private int Orderid; //订单id 主键
    private int uid; // 用户id
    private String realname; // 真实名字
    private String Location; // 所在地区
    private String address; // 详细地址
    private int postcode; // 邮编
    private String phone; // 手机号
    private Date created; // 创建时间
    private Date updated; // 更新时间
}
