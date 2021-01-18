package com.atguigu.dao;

import com.atguigu.pojo.TravelItem;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @author lijian
 * @create 2021-01-04 15:08
 */
public interface TravelItemDao {
    void add(TravelItem travelItem);

    Page<TravelItem> findPage(String queryString);

    void delect(Integer id);

    TravelItem queryById(Integer id);

    void insert(TravelItem travelItem);

    List<TravelItem> queryAll();

    Integer queryTravelGroup(Integer id);

    List<TravelItem> findTravelItemListById(Integer id);
}
