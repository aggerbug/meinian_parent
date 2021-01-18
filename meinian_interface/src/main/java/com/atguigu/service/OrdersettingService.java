package com.atguigu.service;

import com.atguigu.pojo.OrderSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface OrdersettingService {
    void add(ArrayList<OrderSetting> objects);

    List<Map> getOrderSettingByMonth(String data);

    void editNumberByDate(OrderSetting orderSetting);
}
