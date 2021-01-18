package com.atguigu.service;

import com.atguigu.entity.Result;

import java.util.Map;

public interface OrderService {
    void submit(String telephone);

    Result order(Map map) throws Exception;

    Map findById(Integer id) throws Exception;
}
