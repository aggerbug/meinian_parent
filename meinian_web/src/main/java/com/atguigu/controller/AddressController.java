package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Address;
import com.atguigu.service.AddressService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Reference
    AddressService addressService;

    @RequestMapping("/findAllMaps")
    public Map findAllMaps(){
        Map map=new HashMap();

        List<Address> addressList=addressService.findAll();
        List<Map> gridnMaps=new ArrayList<>();

        List<Map> nameMaps=new ArrayList<>();
        for (Address address : addressList) {
            Map gridnMap=new HashMap();
            gridnMap.put("lng",address.getLng());
            gridnMap.put("lat",address.getLat());
            gridnMaps.add(gridnMap);
            Map nameMap=new HashMap();
            nameMap.put("addressName",address.getAddressName());
            nameMaps.add(nameMap);
        }
        map.put("gridnMaps",gridnMaps);
        map.put("nameMaps",nameMaps);

        return map;
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult result=addressService.findPage(queryPageBean);
        return result;
    }

    @RequestMapping("/addAddress")
    private Result addAddress(@RequestBody Address address){
        addressService.add(address);
        return new Result(true,"地址保存成功");

    }

    @RequestMapping("/deleteById")
    public Result deleteById(Integer id){
        addressService.deleteById(id);
        return new Result(true,"已删除地址");
    }
}
