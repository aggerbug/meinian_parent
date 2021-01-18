package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.OrderSettingDao;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrdersettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrdersettingService.class)
@Transactional
public class OrdersettingServiceImpl implements OrdersettingService {
    @Autowired
    OrderSettingDao orderSettingDao;

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        int countByData = orderSettingDao.findCountByData(orderSetting.getOrderDate());
        if(countByData>0){
            orderSettingDao.editNumberByOrderData(orderSetting);
        }else {
            orderSettingDao.add(orderSetting);
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String data) {
        Map map=new HashMap();
        String dataUp=data+"-"+1;
        String dataLast=data+"-"+31;
        map.put("frist",dataUp);
        map.put("last",dataLast);
        List<OrderSetting> list=orderSettingDao.getOrderSettingByMonth(map);
        List<Map> mapList=new ArrayList<>();
        for (OrderSetting orderSetting : list) {
            Map map1=new HashMap();
            map1.put("date",orderSetting.getOrderDate().getDate());
            map1.put("number",orderSetting.getNumber());
            map1.put("reservations",orderSetting.getReservations());
            mapList.add(map1);
        }
        return mapList;
    }

    @Override
    public void add(ArrayList<OrderSetting> orderSettings) {
        for (OrderSetting setting : orderSettings) {
            int count = orderSettingDao.findCountByData(setting.getOrderDate());
            if(count>0){
                orderSettingDao.editNumberByOrderData(setting);
            }else {
                orderSettingDao.add(setting);
            }

        }

    }
}
