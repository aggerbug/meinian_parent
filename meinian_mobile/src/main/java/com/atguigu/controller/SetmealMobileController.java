package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealMobileController {
    @Reference
    SetmealService setmealService;

    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){
        try {
            List<Setmeal> list=setmealService.findAll();
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,list);
        } catch (Exception e) {
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
@RequestMapping("/getSetMealById")
    public Result getSetMealById(Integer id){
        try {
            Setmeal setmeal=setmealService.getSetMealById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }

    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Setmeal setmeal=setmealService.findById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }

    }
}
