package com.atguigu.dao;

import com.atguigu.pojo.Order;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderDao {


    

    void add(Order order);

    List<Order> findByCond(Order order);

    Map findById(Integer id);

    int getTodayOrderNumber(String today);

    int getTodayVisitsNumber(String today);

    int getThisWeekOrderNumber(Map<String, Object> weekMap);

    int getThisWeekVisitsNumber(Map<String, Object> weekMap);

    List<Map<String, Object>> getHotSetmeal();
}
