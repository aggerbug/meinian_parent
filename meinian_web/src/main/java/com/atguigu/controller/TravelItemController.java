package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lijian
 * @create 2021-01-04 14:47
 */
@RestController
@RequestMapping("/travelItem")
public class TravelItemController {

    @Reference //调用远程接口。
            TravelItemService travelItemService;

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        System.out.println("queryPageBean = " + queryPageBean);
        PageResult pageResult = travelItemService.findPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
        return pageResult;
    }


    //@RequestBody 从请求体获取数据 - json数据。
    // SpringMVC框架将请求体数据转换为实体对象。
    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('TRAVELITEM_ADD')")
    public Result add(@RequestBody TravelItem travelItem) {
        try {
            System.out.println("travelItem = " + travelItem);
            travelItemService.add(travelItem);
            return new Result(true, MessageConstant.ADD_TRAVELITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_TRAVELITEM_FAIL);
        }
    }

    @RequestMapping("/insert")
    public Result insert(@RequestBody TravelItem travelItem){
        try {
            travelItemService.insert(travelItem);
            return new Result(true,MessageConstant.EDIT_TRAVELITEM_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.EDIT_TRAVELITEM_FAIL);
        }
    }

    @RequestMapping("/queryById")
    public Result queryById(Integer id) {
        try {
            TravelItem travelItem = travelItemService.queryById(id);
            return new Result(true, MessageConstant.QUERY_TRAVELITEM_SUCCESS, travelItem);
        } catch (Exception e) {
            return new Result(false, MessageConstant.QUERY_TRAVELITEM_FAIL);
        }
    }

    @RequestMapping("/delete")
    @PreAuthorize("hasAuthority('TRAVELITEM_DELETE')")
    public Result delect(Integer id) {
        try {
            travelItemService.delect(id);
            return new Result(true, MessageConstant.DELETE_TRAVELITEM_SUCCESS);
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_TRAVELITEM_FAIL);
        }
    }

    @RequestMapping("/queryAll")
    public Result queryAll(){
        try {
            List<TravelItem> travelItems = travelItemService.queryAll();
            return new Result(true,MessageConstant.QUERY_TRAVELITEM_SUCCESS,travelItems);
        } catch (Exception e) {
            return new Result(false,MessageConstant.QUERY_TRAVELITEM_FAIL);
        }
    }

}
