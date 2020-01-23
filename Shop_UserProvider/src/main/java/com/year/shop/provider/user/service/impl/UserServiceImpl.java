package com.year.shop.provider.user.service.impl;

import com.year.shop.common.config.RedisConfig;
import com.year.shop.common.util.JedisUtil;
import com.year.shop.common.vo.R;
import com.year.shop.provider.user.service.UserSignService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

/**
 * @author fyy
 * @date 2020/1/20 0020 下午 18:29
 */
public class UserServiceImpl implements UserSignService {
    @Autowired
    private UserSignDao userSignDao;

    @Autowired
    private JedisUtil jedisUtil;

    @Autowired
    private UserLeaveDao userLeaveDao;

    @Autowired
    private UserScoreLogDao userScoreLogDao;

    /**
     * 检查今日是否可以签到
     *
     * @param token
     * @return
     */
    @Override
    public R checkDay(String token) {
        int uid = Integer.parseInt(jedisUtil.getStr(RedisConfig.TOKEN_KEY + token));
        if (userSignDao.selectByUidDay(uid) != null) { // 检查今天 该用户 是否签到
            return R.fail("今日已签到");
        } else {
            return R.ok();
        }
    }

    /**
     * 查询当前月的签到数据
     *
     * @param token
     * @return
     */
    @Override
    public R queryMonth(String token) {
        int uid = Integer.parseInt(jedisUtil.getStr(RedisConfig.TOKEN_KEY + token));
        return R.ok(userSignDao.selectMonth(uid));
    }

    /**
     * 添加 签到 实现签到 -奖励积分 --等级 --积分变动
     *
     * @param token
     * @return
     */
    @Override
    public R sign(String token) {
        /*
        * 1、基础奖励：1-3分
			2、连续奖励：
				连续3天：奖励翻倍
				连续7天：额外奖励 5积分
				连续30天：额外奖励 100积分
			3、首次签到：20积分 */
        // 从token中获取用户id
        int uid = Integer.parseInt(jedisUtil.getStr(RedisConfig.TOKEN_KEY + token));
        //// 获取最近一次签到的信息
        UserSign sign = userSignDao.selectLast(uid);
        Random random = new Random();
        UserSign userSign;
        if (sign != null) { //  数据库有信息 之前签过到
            // 判断今天是否签到
            // 用从数据库获取的日期 - DateUtil中获取到的今天的日期 == 0 表示数据库的信息是今天的日期
            if (sign.getCdate().compareTo(DateUtil.getDate()) == 0) { // 今天的日期
                // 今天已经签到
                return R.fail("今日已经签到");
            } else { // 今天未签到
                userSign = new UserSign();
                userSign.setUid(uid); // 用户id
                userSign.setDays(sign.getDays() + 1); // 未签到  用昨天的签到时间+1=今天日期 设置今天的日期
                userSign.setScore(random.nextInt(3) + 1); // 设置随机积分
                // 检查是否为连续签到
                if (sign.getCdate().compareTo(DateUtil.addDate(-1)) == 0) {
                    // 连续签到校验
                    if (userSign.getDays() % 30 == 0) {
                        // 30天的倍数
                        userSign.setExtrascore(100);
                    } else if (userSign.getDays() % 7 == 0) {
                        userSign.setExtrascore(5);
                    } else if (userSign.getDays() % 3 == 0) {
                        userSign.setExtrascore(userSign.getScore());
                    }
                }
            }
            // 之前未签到， 该用户第一次签到
        } else {
            userSign = new UserSign();
            userSign.setDays(1);
            userSign.setScore(random.nextInt(3) + 1);
            userSign.setExtrascore(20);
            userSign.setUid(uid);
        }
        // 添加签到记录
        userSignDao.save(userSign);
        // 查询用户等级积分
        UserLevel userLevel = userLeaveDao.selectByUid(uid);
        // 签到获取的积分= 普通签到 + 额外奖励积分
        int sc = userSign.getScore() + userSign.getExtrascore();
        Boolean isLeave = false;
        // 原来的积分等级
        if (userLevel.getSocre() < SystemConfig.LEVEL_1) {
            // 判断签到后的积分
            if (userLevel.getSocre() + sc >= SystemConfig.LEVEL_1) {
                // 升级
                isLeave = true;
                userLevel.setName(LeaveName.XIUCAI.getName());
            }
        } else if (userLevel.getSocre() < SystemConfig.LEVEL_2) {
            // 判断签到后的积分
            if (userLevel.getSocre() + sc >= SystemConfig.LEVEL_2) {
                // 升级
                isLeave = true;
                userLevel.setName(LeaveName.TONGSHENG.getName());
            }
        } else if (userLevel.getSocre() < SystemConfig.LEVEL_3) {
            // 判断签到后的积分
            if (userLevel.getSocre() + sc >= SystemConfig.LEVEL_3) {
                // 升级
                isLeave = true;
                userLevel.setName(LeaveName.JUREN.getName());
            }
        }
        // 积分
        userLevel.setSocre(userLevel.getSocre() + sc);
        if (isLeave) {
            // 修改等级和积分
            userLeaveDao.updateName(uid, userLevel.getName(), userLevel.getSocre());
        } else { // 修改积分
            userLeaveDao.updatescore(uid, userLevel.getSocre());
        }
        // 积分变更日志
        UserScoreLog userScoreLog = new UserScoreLog();
        userScoreLog.setUid(uid);
        userScoreLog.setScore(sc);
        userScoreLog.setInfo("签到奖励的积分，连续签到：" + userSign.getDays());
        userScoreLogDao.insert(userScoreLog);
        return R.ok(userSign);
    }

    /**
     * 查询签到的记录 倒叙排列
     *
     * @param token
     * @return
     */
    @Override
    public R queryAll(String token) {
        int uid = Integer.parseInt(jedisUtil.getStr(RedisConfig.TOKEN_KEY + token));
        return R.ok(userSignDao.selectAll(uid));
    }
}
