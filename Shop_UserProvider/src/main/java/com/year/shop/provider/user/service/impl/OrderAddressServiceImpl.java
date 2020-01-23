package com.year.shop.provider.user.service.impl;

import com.year.shop.common.config.RedisConfig;
import com.year.shop.common.dto.user.OrderAddressDto;
import com.year.shop.common.util.JedisUtil;
import com.year.shop.common.vo.R;
import com.year.shop.entity.user.OrderAddress;
import com.year.shop.provider.user.dao.OrderAddressDao;
import com.year.shop.provider.user.service.OrderAddressService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author fyy
 * @date 2020/1/23 0023 下午 13:43
 */
public class OrderAddressServiceImpl implements OrderAddressService {

    @Autowired
    private OrderAddressDao addressDao;

    @Autowired
    private JedisUtil jedisUtil;

    @Override
    public R save(OrderAddress address) {

        return R.ok(addressDao.add(address));
    }

    @Override
    public R selectAll(String token) {
        int uid = Integer.parseInt(jedisUtil.getStr(RedisConfig.TOKEN_KEY + token));
        return R.ok(addressDao.selectAll(uid));
    }

    @Override
    public R update(String token,OrderAddressDto addressDto) {
        int uid = Integer.parseInt(jedisUtil.getStr(RedisConfig.TOKEN_KEY + token)); // 从token中获取用户id
        addressDto.setUid(uid);
        if (addressDao.update(addressDto) > 0) { // 修改信息
            return R.ok();
        } else {
            return R.fail();
        }
    }

    @Override
    public R delete(int orderid,String token) {
        int uid = Integer.parseInt(jedisUtil.getStr(RedisConfig.TOKEN_KEY + token)); // 从token中获取用户id

        return R.ok(addressDao.delete(orderid,uid));
    }
}
