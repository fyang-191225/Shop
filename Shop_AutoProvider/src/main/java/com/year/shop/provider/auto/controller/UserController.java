package com.year.shop.provider.auto.controller;

import com.year.shop.common.config.SystemConfig;
import com.year.shop.common.dto.user.UserLoginDto;
import com.year.shop.common.exception.UserException;
import com.year.shop.common.vo.R;
import com.year.shop.entity.user.User;
import com.year.shop.provider.auto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author fyy
 * @date 2020/1/20 0020 下午 18:04
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @PostMapping("/provider/user/register")
    public R save(@RequestBody User user) {
        return userService.save(user);
    }

    /**
     * 校验
     *
     * @param name
     * @return
     */
    @GetMapping("/provider/user/checkname/{0}")
    public R check(@PathVariable String name) {
        return userService.checkName(name);
    }

    /**
     * 用于找回密码
     *
     * @param user
     * @return
     */
    @PostMapping("/provider/user/forgetpass")
    public R getPass(@RequestBody User user) {
        return userService.forgetPwd(user.getPhone(), user.getPassword());
    }

    //登录
    @PostMapping("/provider/user/login")
    public R login(@RequestBody UserLoginDto loginDto) {
        return userService.login(loginDto);
    }

    //校验登录
    @GetMapping("/provider/user/checklogin")
    public R checkLogin(HttpServletRequest request) throws UserException {
        return userService.checkToken(request.getHeader(SystemConfig.TOKEN_HEAD));
    }
}

