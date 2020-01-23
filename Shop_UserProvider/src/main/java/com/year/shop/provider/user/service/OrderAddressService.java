package com.year.shop.provider.user.service;

import com.year.shop.common.dto.user.OrderAddressDto;
import com.year.shop.common.vo.R;
import com.year.shop.entity.user.OrderAddress;

/**
 * @author fyy
 * @date 2020/1/23 0023 下午 13:39
 */
public interface OrderAddressService {

    // 新增
    R save(OrderAddress address);

    // 查询
    R selectAll(String token);

    // 修改
    R update(String token,OrderAddressDto addressDto);

    // 删除
    R delete(int orderid,String token);
}
