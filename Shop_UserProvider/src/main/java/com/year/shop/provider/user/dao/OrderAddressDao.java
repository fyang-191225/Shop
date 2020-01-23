package com.year.shop.provider.user.dao;

import com.year.shop.common.dto.user.OrderAddressDto;
import com.year.shop.entity.user.OrderAddress;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author fyy
 * @date 2020/1/23 0023 下午 12:51
 */
public interface OrderAddressDao {
    // 新增
    @Insert("insert into t_order_address(uid,realname,location,address,postcode,phone,created,updated)values (#{uid},#{realname},#{location},#{address},#{postcode},#{phone},now(),now())")
    int add(OrderAddress address);

    // 查询
    @Select("select * from t_order_address where uid = #{uid} order by created")
    List<OrderAddress> selectAll(int uid);

    // 修改
    @Update("update t_order_address set realname =#{realname},location=#{location},address=#{address},postcode=#{postcode},phone=#{phone},updated=now() where uid=#{uid} and orderid = #{orderid}")
    int update(OrderAddressDto addressDto);

    // 删除
    @Delete("delete from t_order_address where orderid = #{orderid} and uid = #{uid} ")
    int delete(int orderid, int uid);
}
