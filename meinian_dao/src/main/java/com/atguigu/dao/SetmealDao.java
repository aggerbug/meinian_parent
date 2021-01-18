package com.atguigu.dao;

import com.atguigu.pojo.Setmeal;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface SetmealDao {
    void addSeteal(Setmeal setmeal);

    void addSetmealOrTrave(Map<String, Integer> map);

    Page<Setmeal> findPage(String queryString);

    List<Setmeal> findAll();

    Setmeal findById(Integer id);

    Setmeal getSetMealById(Integer id);

    List<Map<String, Object>> findSetmealCount();
}
