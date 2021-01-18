package com.atguigu.dao;

import com.atguigu.pojo.Role;

import java.util.List;

public interface RoleDao {
    List<Role> findRolesByUserId(Integer id);
}
