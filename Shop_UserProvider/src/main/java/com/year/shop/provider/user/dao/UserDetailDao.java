package com.year.shop.provider.user.dao;


import com.year.shop.common.dto.user.UserDetailDto;
import com.year.shop.entity.user.UserDetail;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author fyy
 * @date 2020/1/2 0002 下午 19:39
 */
public interface UserDetailDao {
    /**
     * 查询我的基本信息
     * @param uid
     * @return
     */
    @Select("select ud.*,u.phone, u.email, u.username from t_user_detail ud inner join t_user u on ud.uid = u.id where ud.uid=#{uid}")
    @ResultType(UserDetailDto.class)
    UserDetailDto selectDetail(int uid);

    /**
     * 修改我的基本信息
     * @param detail
     * @return
     */
    @Update("update t_userdetail set nickname =#{nickname}, realname =#{realname},sex=#{sex},birthday=#{birthday},address =#{address},image=#{image},created=now() where uid=#{uid}")
    int update(UserDetail detail);

}
