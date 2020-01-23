package com.year.shop.provider.user.service;

import com.year.shop.common.vo.R;
import com.year.shop.entity.user.UserDetail;

/**
 * @author fyy
 * @date 2020/1/23 0023 下午 15:16
 */
public interface UserDetailService {

    /**
     * 查询详情：用户信息和详情信息
     * @param token
     * @return
     */
    R queryDetil(String token);

    /**
     * 完善资料 更改用户详情
     * @return
     */
    R changeDetil(String token , UserDetail detail );
}

