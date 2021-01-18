package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealService {
    void add(Integer[] travelgroupIds, Setmeal setmeal);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    List<Setmeal> findAll();

    Setmeal findById(Integer id);

    Setmeal getSetMealById(Integer id);

    List<Map<String, Object>> findSetmealCount();

    Map<String, Object> getBusinessReportData();
}
