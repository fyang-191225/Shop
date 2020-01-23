package com.year.shop.provider.auto.service;

import com.year.shop.common.dto.user.UserLoginDto;
import com.year.shop.common.vo.R;
import com.year.shop.entity.user.User;
import com.year.shop.common.exception.UserException;

/**
 * @author fyy
 * @date 2020/1/20 0020 下午 17:43
 */
public interface UserService {

    /**
     * 添加新用户 用于注册新用户
     * @param user
     * @return
     */
    R save(User user);

    /**
     * 登录
     * @param userLoginDto
     * @return
     */
    R login(UserLoginDto userLoginDto);

    /**
     * 注册前的校验，校验该用户是否存在
     * @param name
     * @return
     */
    R checkName(String name);

    /**
     * 修改密码 登陆后
     * @return
     */
    R changePwd(String token, String pass);

    /**
     * 忘记密码
     * @param phone
     * @param pass
     * @return
     */
    R forgetPwd(String phone, String pass);

    /**
     * 校验令牌
     * @param token
     * @return
     */
    R checkToken(String token) throws UserException;
}
