package com.year.shop.provider.user.service.impl;

import com.year.shop.common.config.RedisConfig;
import com.year.shop.common.util.JedisUtil;
import com.year.shop.common.vo.R;
import com.year.shop.entity.user.UserDetail;
import com.year.shop.provider.user.dao.UserDetailDao;
import com.year.shop.provider.user.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author fyy
 * @date 2020/1/23 0023 下午 15:17
 */
public class UserDetailServiceImpl implements UserDetailService {

    @Autowired
    private UserDetailDao userDetilDao;

    @Autowired
    private JedisUtil jedisUtil;

    /**
     * 查询详情
     * @param token
     * @return
     */
    @Override
    public R queryDetil(String token) {
        int uid = Integer.parseInt(jedisUtil.getStr(RedisConfig.TOKEN_KEY + token)); // 从token中获取用户id
        return R.ok(userDetilDao.selectDetail(uid));// 返回我的基本信息
    }

    /**
     * 修改我的基本信息
     *
     * @param token
     * @param detail
     * @return
     */
    @Override
    public R changeDetil(String token, UserDetail detail) {
        int uid = Integer.parseInt(jedisUtil.getStr(RedisConfig.TOKEN_KEY + token)); // 从token中获取用户id
        detail.setUid(uid);
        if (userDetilDao.update(detail) > 0) { // 修改信息
            return R.ok();
        } else {
            return R.fail();
        }
    }
}

