package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.pojo.TravelItem;

import java.util.List;

public interface TravelItemService {
    void add(TravelItem travelItem);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    void delect(Integer id);

    TravelItem queryById(Integer id);

    void insert(TravelItem travelItem);

    List<TravelItem> queryAll();
}
