package com.atguigu.dao;

import com.atguigu.pojo.TravelGroup;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface TravelGroupDao {
    void add(TravelGroup travelGroup);

    void setTravelGroupAndTravelItem(Map<String, Integer> map);

    Page<TravelGroup> query(String queryString);

    TravelGroup findById(Integer id);

    List<Integer> findTravelItemIdByTravelgroupId(Integer id);

    void edit(TravelGroup travelGroup);

    void deleteTravelGroupAndTravelItemByTravelGroupId(Integer id);

    void delete(Integer id);

    List<TravelGroup> queryAll();

    //辅助
    List<TravelGroup> findTravelGroupListById(Integer id);

}
