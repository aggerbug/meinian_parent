package com.atguigu.dao;

import com.atguigu.pojo.OrderSetting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {


    int findCountByData(Date orderDate);

    void editNumberByOrderData(OrderSetting setting);

    void add(OrderSetting setting);

    List<OrderSetting> getOrderSettingByMonth(Map map);

    OrderSetting findByOrderDate(Date date1);

    void editReservationsByOrderDate(OrderSetting orderSetting);
}
