package com.atguigu.dao;

import com.atguigu.pojo.User;

public interface UserDao {
    User findByUserName(String username);
}
