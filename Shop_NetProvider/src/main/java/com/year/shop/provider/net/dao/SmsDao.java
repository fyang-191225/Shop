package com.year.shop.provider.net.dao;

import com.fyy.mybabystudy.entity.net.Sms;
import org.apache.ibatis.annotations.Insert;

/**
 * @author fyy
 * @date 2019/12/30 0030 下午 19:37
 */
public interface SmsDao {

    @Insert("insert into t_sms(phone,tempname,content,type,ctime) values (#{phone},#{tempname},#{content},#{type},now())")
    int insert(Sms sms);
}
