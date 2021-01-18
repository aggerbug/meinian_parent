package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.RedisConstant;
import com.atguigu.dao.MemberDao;
import com.atguigu.dao.OrderDao;
import com.atguigu.dao.SetmealDao;
import com.atguigu.entity.PageResult;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.SetmealService;
import com.atguigu.utils.DateUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    SetmealDao setmealDao;

    @Autowired
    JedisPool jedisPool;

    @Autowired
    MemberDao memberDao;

    @Autowired
    OrderDao orderDao;

    @Override
    public List<Map<String, Object>> findSetmealCount() {
        return setmealDao.findSetmealCount();
    }

    @Override
    public Setmeal getSetMealById(Integer id) {
        Setmeal setmeal=setmealDao.getSetMealById(id);
        return setmeal;
    }

    @Override
    public Map<String, Object> getBusinessReportData() {
        Map<String, Object> map=new HashMap<>();

        try {
            //获取当前日期
            String today= DateUtils.parseDate2String(DateUtils.getToday());
            map.put("reportDate",today);

            //今日新增会员数
            int todayMemeber=memberDao.getTodayNewMember(today);
            map.put("todayNewMember",todayMemeber);

            //总会员数
            int totalMember=memberDao.getTotalMember();
            map.put("totalMember",totalMember);

            //本周第一天
            String weekMonday=DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
            //本周最后一天
            String weekSunday=DateUtils.parseDate2String(DateUtils.getSundayOfThisWeek());
            //本月第一天
            String monthFirst=DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
            //本月最后一天
            String monthLast=DateUtils.parseDate2String(DateUtils.getLastDay4ThisMonth());
            //本周新增会员数
            int thisWeekNewMember=memberDao.getThisweekAndMonthNewMenber(weekMonday);
            map.put("thisWeekNewMember",thisWeekNewMember);

            //本月新增会员数
            int thisMonthNewMember=memberDao.getThisweekAndMonthNewMenber(monthFirst);
            map.put("thisMonthNewMember",thisMonthNewMember);

            //当天预约数
            int todayOrderNumber=orderDao.getTodayOrderNumber(today);
            map.put("todayOrderNumber",todayOrderNumber);

            //本日出游人数
            int todayVisitsNumber=orderDao.getTodayVisitsNumber(today);
            map.put("todayVisitsNumber",todayVisitsNumber);

            //thisWeekOrderNumber
            //本周预约数
            Map<String,Object> weekMap=new HashMap();
            weekMap.put("begin",weekMonday);
            weekMap.put("end",weekSunday);
            int thisWeekOrderNumber=orderDao.getThisWeekOrderNumber(weekMap);
            map.put("thisWeekOrderNumber",thisWeekOrderNumber);

            //本周出行数
            int thisWeekVisitsNumber=orderDao.getThisWeekVisitsNumber(weekMap);
            map.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
            //本月预约数
            Map<String,Object> monthMap=new HashMap<>();
            monthMap.put("begin",monthFirst);
            monthMap.put("end",monthLast);
            int thisMonthOrderNumber = orderDao.getThisWeekOrderNumber(monthMap);
            map.put("thisMonthOrderNumber",thisMonthOrderNumber);

            //本月出行数
            int thisMonthVisitsNumber = orderDao.getThisWeekVisitsNumber(monthMap);
            map.put("thisMonthVisitsNumber",thisMonthVisitsNumber);

            //热门套餐
            List<Map<String,Object>> hotSetmeal=orderDao.getHotSetmeal();
            map.put("hotSetmeal",hotSetmeal);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    @Override
    public Setmeal findById(Integer id) {
        Setmeal setmeal=setmealDao.findById(id);
        return setmeal;
    }

    @Override
    public List<Setmeal> findAll() {
        List<Setmeal> list=setmealDao.findAll();
        return list;
    }

    @Override
    public PageResult findPage(Integer currentPage,Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page=setmealDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void add(Integer[] travelgroupIds, Setmeal setmeal) {
        setmealDao.addSeteal(setmeal);
        addSetmealOrTrave(travelgroupIds,setmeal.getId());
        savePic2Redis(setmeal.getImg());
    }

    private void savePic2Redis(String pic){
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,pic);
    }

    public void addSetmealOrTrave(Integer[] travelgroupIds,Integer setmealId){
        for (Integer travelgroupId : travelgroupIds) {
            Map<String,Integer> map=new HashMap<>();
            map.put("travelgroupId",travelgroupId);
            map.put("setmealId",setmealId);
            setmealDao.addSetmealOrTrave(map);
        }
    }
}
