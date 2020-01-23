package com.year.shop.provider.user.dao;

import com.year.shop.entity.user.UserSign;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author fyy
 * @date 2020/1/20 0020 下午 17:43
 */
public interface UserSignDao {
    /**
     * 查询今日是否可以签到
     *
     * @param uid
     * @return
     */
    @Select("select * from t_user_sign where uid = #{uid} and cdate = date_format(now(),'%Y-%m-%d')")
    @ResultType(UserSign.class)
    UserSign selectByUidDay(int uid);

    /**
     * 查询某用户今天的签到数据 即查询当前月的签到数据
     * @param uid
     * @return
     */
    @Select("select * from t_usersign where uid = #{uid} and date_format(cdate,'%Y-%m') = date_format(now(),'%Y-%m') order by cdate")
    List<UserSign> selectMonth(int uid);

    /**
     * 添加 签到
     * @param sign
     * @return
     */
    @Insert("insert into t_usersign(uid,score,extrascore,days,cdate,ctime) values (#{uid},#{score},#{extrascore},#{days},now())")
    int save(UserSign sign);

    /**
     * 获取最近一次签到的信息 根据时间的倒叙来查出
     * @param uid
     * @return
     */
    @Select("select * from t_usersign where uid = #{} order by cdate desc limit 1")
    @ResultType(UserSign.class)
    UserSign selectLast(int uid);

    /**
     * 查询该用户的等级 的全部信息
     * @param uid
     * @return
     */
    @Select("select * from t_userlevel where uid=#{uid} order by cdate desc")
    @ResultType(UserSign.class)
    List<UserSign> selectAll(int uid);
}

}
