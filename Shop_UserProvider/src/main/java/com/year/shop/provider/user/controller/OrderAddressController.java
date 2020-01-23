package com.year.shop.provider.user.controller;

import com.year.shop.common.config.SystemConfig;
import com.year.shop.common.dto.user.OrderAddressDto;
import com.year.shop.common.vo.R;
import com.year.shop.entity.user.OrderAddress;
import com.year.shop.provider.user.service.OrderAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author fyy
 * @date 2020/1/23 0023 下午 13:54
 */
@RestController
public class OrderAddressController {

    @Autowired
    private OrderAddressService addressService;

    // 新增
    @PostMapping("/provider/orderaddress/save")
    public R save(OrderAddress address) {
        return addressService.save(address);
    }

    // 查询
    @GetMapping("/provider/orderaddress/selectall")
    public R selectAll(HttpServletRequest request) {
        return addressService.selectAll(request.getHeader(SystemConfig.TOKEN_HEAD));
    }

    // 修改
    @PutMapping("/provider/orderaddress/update")
    public R update(HttpServletRequest request,@RequestBody OrderAddressDto addressDto) {
        return addressService.update(request.getHeader(SystemConfig.TOKEN_HEAD), addressDto);
    }

    // 删除
    @DeleteMapping("/provider/orderaddress/delete")
    public R delete(int orderid, HttpServletRequest request) {
        return addressService.delete(orderid, request.getHeader(SystemConfig.TOKEN_HEAD));
    }
}
