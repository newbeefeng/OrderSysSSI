package com.chinasofti.ordersys.mapper;

import com.chinasofti.ordersys.vo.OrderInfo;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.Mapper;

import java.util.List;

@Mapper
public interface InfoList {

    //获取订单总额
    @Select("select sum(dishesPrice * num) sumMoney\n" +
            "from orderinfo, userinfo, dishesinfo, orderdishes\n" +
            "where orderState = 0 and userId = waiterId and orderId = orderReference and dishes = dishesId\n" +
            "and orderId = #{orderId};")
    Double getSumMoney(Integer orderId);

    //获取所有的菜品信息
    @Select("select dishesName, dishesPrice, num dishesCount\n" +
            "from orderinfo, userinfo, dishesinfo, orderdishes\n" +
            "where orderState = 0 and userId = waiterId and orderId = orderReference and dishes = dishesId and orderId = #{orderId};")
    List<OrderInfo> getDishesList(int orderId);

    @Select("select userAccount from userinfo where userId = #{waiterId};")
    String getWaiterName(int waiterId);
}
