package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Order;
import com.atguigu.service.OrderService;
import com.atguigu.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;


import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Reference
    OrderService orderService;

    @Autowired
    JedisPool jedisPool;

    @RequestMapping("/submit")
    public Result submit(String telephone){
        try {
            orderService.submit(telephone);
            return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

    @RequestMapping("/submitMap")
    public Result submitMap(@RequestBody Map map){
        String telephone = map.get("telephone").toString();
        String code = map.get("validateCode").toString();
        String telephonecode=jedisPool.getResource().get(telephone+ RedisMessageConstant.SENDTYPE_ORDER);
        if(telephone==null&&!telephonecode.equals(code)){
            return new Result(false,MessageConstant.VALIDATECODE_ERROR);
        }
        Result result=null;
        try {
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            result=orderService.order(map);
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        if (result.isFlag()){
            String orderDate = map.get("orderDate").toString();
            try {
                SMSUtils.sendShortMessage(telephone,orderDate);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return result;
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        Map map=null;
        try {
            map=orderService.findById(id);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

}
