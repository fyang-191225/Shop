package com.year.shop.provider.user.controller;

import com.year.shop.common.config.SystemConfig;
import com.year.shop.common.vo.R;
import com.year.shop.entity.user.UserDetail;
import com.year.shop.provider.user.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author fyy
 * @date 2020/1/23 0023 下午 15:19
 */

@RestController
public class UserDetailController {
    @Autowired
    private UserDetailService userDetailService;

    /**
     * 查询我的基本信息
     *
     * @param request
     * @return
     */
    @GetMapping("/provider/userdetail/detail")
    public R detail(HttpServletRequest request) {
        return userDetailService.queryDetil(request.getHeader(SystemConfig.TOKEN_HEAD));
    }

    /**
     * 修改信息
     *
     * @param detail
     * @param request
     * @return
     */
    @PutMapping("/provider/userdetail/changedetail")
    public R changeUpdate(@RequestBody UserDetail detail, HttpServletRequest request) {
        return userDetailService.changeDetil(request.getHeader(SystemConfig.TOKEN_HEAD), detail);
    }
}

