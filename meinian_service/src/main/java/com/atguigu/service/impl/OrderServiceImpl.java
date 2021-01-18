package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.dao.MemberDao;
import com.atguigu.dao.OrderDao;
import com.atguigu.dao.OrderSettingDao;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Member;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrderService;
import com.atguigu.utils.DateUtils;
import com.atguigu.utils.SMSUtils;
import com.atguigu.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    JedisPool jedisPool;

    @Autowired
    OrderDao orderDao;

    @Override
    public Map findById(Integer id) throws Exception {
        Map map=orderDao.findById(id);
        if(map != null){
            //处理日期格式
            Date orderDate = (Date) map.get("orderDate");
            map.put("orderDate",DateUtils.parseDate2String(orderDate));
            return map;
        }

        return map;
    }

    @Autowired
    OrderSettingDao orderSettingDao;

    @Autowired
    MemberDao memberDao;

    @Override
    public Result order(Map map) throws Exception {
        String orderDate = map.get("orderDate").toString();
        String date=(String)map.get("orderDate");
        System.out.println(date);
        System.out.println(orderDate);
        Date date1=DateUtils.parseString2Date(orderDate);
        OrderSetting orderSetting=orderSettingDao.findByOrderDate(date1);//查询预约是否存在
        if(orderSetting==null){
            return new Result(false,MessageConstant.SELECTED_DATE_CANNOT_ORDER);//没这天
        }else {
            int number = orderSetting.getNumber();
            int reservations = orderSetting.getReservations();//预约天数是否超过预设值
            if(reservations>=number){
                return new Result(false,MessageConstant.ORDER_FULL);
            }
        }
        String telephone = map.get("telephone").toString();
        Member member=memberDao.findByTelephone(telephone);//查询用户是否注册
        if(member!=null){
            Integer id = member.getId();
            int setmealId = Integer.parseInt(map.get("setmealId").toString());
            Order order=new Order(id,date1,null,null,setmealId);

            //查询是否已经预约过此日期
            List<Order> list=orderDao.findByCond(order);
            if(list!=null&&list.size()>0){
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }else {
            //没注册
            member=new Member();
            member.setName(map.get("name").toString());
            member.setSex(map.get("sex").toString());
            member.setPhoneNumber(telephone);
            member.setIdCard(map.get("idCard").toString());
            member.setRegTime(new Date());
            memberDao.add(member);
        }

        orderSetting.setReservations(orderSetting.getReservations()+1);
        orderSettingDao.editReservationsByOrderDate(orderSetting);

        Order order=new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(date1);
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        order.setOrderType(map.get("orderType").toString());
        order.setSetmealId(Integer.parseInt(map.get("setmealId").toString()));
        orderDao.add(order);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order);
    }

    @Override
    public void submit(String telephone) {
        try {
            String string = ValidateCodeUtils.generateValidateCode(4).toString();
            SMSUtils.sendShortMessage(telephone,string);
            jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_ORDER,300,string);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
