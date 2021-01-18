package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.service.TravelGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/travaelGroup")
public class TravelGroupController {

    @Reference
    TravelGroupService travelGroupService;

    @RequestMapping("/queryAll")
    public Result queryAll(){
        try {
            List<TravelGroup> list=travelGroupService.queryAll();
            return new Result(true,MessageConstant.QUERY_TRAVELGROUP_SUCCESS,list);
        } catch (Exception e) {
            return new Result(false,MessageConstant.QUERY_TRAVELGROUP_FAIL);
        }
    }

    @RequestMapping("/add")
    public Result add(Integer[] travelItemIds, @RequestBody TravelGroup travelGroup){
        try {
            travelGroupService.add(travelItemIds,travelGroup);
            return new Result(true, MessageConstant.ADD_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.ADD_TRAVELGROUP_FAIL);
        }
    }
    @RequestMapping("/query")
    public PageResult query(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult=travelGroupService.query(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
        return pageResult;
    }
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            TravelGroup travelGroup=travelGroupService.findById(id);
            return new Result(true,MessageConstant.QUERY_TRAVELGROUP_SUCCESS,travelGroup);
        } catch (Exception e) {
            return new Result(false,MessageConstant.QUERY_TRAVELGROUP_FAIL);
        }

    }

    @RequestMapping("/findTravelItemIdByTravelgroupId")
    public List<Integer> findTravelItemIdByTravelgroupId(Integer id){
        List<Integer> list =  travelGroupService.findTravelItemIdByTravelgroupId(id);
        return list;
    }
    @RequestMapping("/edit")
    public Result edit(Integer[] travelItemIds,@RequestBody TravelGroup travelGroup ){
        travelGroupService.edit(travelItemIds,travelGroup);
        return new Result(true,MessageConstant.EDIT_TRAVELGROUP_SUCCESS);
    }

    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            travelGroupService.delete(id);
            return new Result(true,MessageConstant.DELETE_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.DELETE_TRAVELGROUP_FAIL);
        }
    }
}
