package com.year.shop.provider.auto.dao;

import com.year.shop.entity.user.User;
import org.apache.ibatis.annotations.*;

/**
 * @author fyy
 * @date 2020/1/20 0020 下午 17:43
 */
public interface UserDao {
    /**
     * 添加新用户
     *
     * @param user
     * @return
     */
    @Insert("insert into ldbz_user(username,password,phone,email,created,updated) VALUES (#{username},#{password},#{phone},#{email},now(),now())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    /**
     * 用于校验该信息是否存在
     *
     * @param name
     * @return
     */
    @Select("select id,username,password,phone,email,created,updated from ldbz_user where username=#{name} or phone=#{name}")
    @ResultType(User.class)
    User selectByName(String name);

    /**
     * 通过phone或者username从数据库获取信息，用于注册时校验数据库是否存在
     *
     * @param user
     * @return
     */
    @Select("select id from ldbz_user where phone=#{phone} or username=#{username}")
    User selectUser(User user);

    /**
     * 用于用户的密码修改
     *
     * @param uid
     * @param password
     * @return
     */
    @Update("update ldbz_user set password = #{password},updated=now() where id = #{uid}")
    int update(@Param("uid") int uid, @Param("password") String password);

    /**
     * 用于忘记密码的修改密码
     *
     * @param phone
     * @param password
     * @return
     */
    @Update("update ldbz_user set password = #{password},updated=now() where phone=#{phone}")
    int update(@Param("phone") String phone, @Param("password") String password);
}



