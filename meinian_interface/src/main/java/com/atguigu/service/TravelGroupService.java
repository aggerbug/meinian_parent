package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.pojo.TravelGroup;

import java.util.List;

public interface TravelGroupService {
    void add(Integer[] travelItemIds, TravelGroup travelGroup);

    PageResult query(Integer currentPage, Integer pageSize, String queryString);

    TravelGroup findById(Integer id);

    List<Integer> findTravelItemIdByTravelgroupId(Integer id);

    void edit(Integer[] travelItemIds, TravelGroup travelGroup);

    void delete(Integer id);

    List<TravelGroup> queryAll();
}
