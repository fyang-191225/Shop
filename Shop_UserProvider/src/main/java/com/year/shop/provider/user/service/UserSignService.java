package com.year.shop.provider.user.service;

import com.year.shop.common.vo.R;

/**
 * @author fyy
 * @date 2020/1/20 0020 下午 17:43
 */
public interface UserSignService {

    //检查今日是否可以签到
    R checkDay(String token);

    //查询当前月的签到数据
    R queryMonth(String token);

    //实现签到 -奖励积分 --等级 --积分变动
    R sign(String token);

    //查询签到的记录 倒叙排列
    R queryAll(String token);
}
