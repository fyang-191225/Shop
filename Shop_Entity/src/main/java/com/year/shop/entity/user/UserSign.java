package com.year.shop.entity.user;

import lombok.Data;

import java.util.Date;

/**
 * @author fyy
 * @date 2020/1/23 0023 下午 15:32
 */
@Data
public class UserSign {
    private Integer id; // 主键id
    private Integer uid; // 用户id
    private Integer days; // 签到的天数
    private Date cdate; // 签到每天的时间，具体到天
    private Date ctime; // 签到的时间
    private Integer score; // 积分
    private Integer extrascore; // 额外奖励
}
