package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.TravelGroupDao;
import com.atguigu.entity.PageResult;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.service.TravelGroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = TravelGroupService.class)
@Transactional
public class TravelGroupServiceImpl implements TravelGroupService {
    @Autowired
    TravelGroupDao travelGroupDao;

    @Override
    public List<Integer> findTravelItemIdByTravelgroupId(Integer id) {
        List<Integer> list=travelGroupDao.findTravelItemIdByTravelgroupId(id);
        return list;
    }

    @Override
    public void edit(Integer[] travelItemIds, TravelGroup travelGroup) {
        travelGroupDao.edit(travelGroup);

        travelGroupDao.deleteTravelGroupAndTravelItemByTravelGroupId(travelGroup.getId());
        // 再新增页面选中的数据
        setTravelGroupAndTravelItem(travelGroup.getId(),travelItemIds);
    }

    @Override
    public void delete(Integer id) {
        travelGroupDao.delete(id);
    }

    @Override
    public TravelGroup findById(Integer id) {
        TravelGroup travelGroup=travelGroupDao.findById(id);
        return travelGroup;
    }

    @Override
    public List<TravelGroup> queryAll() {
        List<TravelGroup> list=travelGroupDao.queryAll();
        return list;
    }

    @Override
    public PageResult query(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<TravelGroup> page=travelGroupDao.query(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void add(Integer[] travelItemIds, TravelGroup travelGroup) {
        travelGroupDao.add(travelGroup);
        setTravelGroupAndTravelItem(travelGroup.getId(),travelItemIds);
    }

    private void setTravelGroupAndTravelItem(Integer travelGroupId,Integer[] travelItemIds){
        for (Integer travelItemId : travelItemIds) {
            // 如果有多个参数使用map
            Map<String, Integer> map = new HashMap<>();
            map.put("travelGroup",travelGroupId);
            map.put("travelItem",travelItemId);
            travelGroupDao.setTravelGroupAndTravelItem(map);
        }
    }
}
