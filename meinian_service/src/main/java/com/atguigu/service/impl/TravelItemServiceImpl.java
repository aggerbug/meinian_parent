package com.atguigu.service.impl;

import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelItemService;
import com.atguigu.entity.PageResult;
import com.atguigu.dao.TravelItemDao;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageHelper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = TravelItemService.class)
@Transactional
public class TravelItemServiceImpl implements TravelItemService {

    @Autowired
    TravelItemDao travelItemDao;
    @Override
    public void add(com.atguigu.pojo.TravelItem travelItem) {
        travelItemDao.add(travelItem);
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<TravelItem> page=travelItemDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public TravelItem queryById(Integer id) {
        TravelItem travelItem=travelItemDao.queryById(id);
        return travelItem;
    }

    @Override
    public List<TravelItem> queryAll() {
        List<TravelItem> travelItems=travelItemDao.queryAll();
        return travelItems;
    }

    @Override
    public void insert(TravelItem travelItem) {
        travelItemDao.insert(travelItem);
    }

    @Override
    public void delect(Integer id) {

        travelItemDao.delect(id);
    }
}
