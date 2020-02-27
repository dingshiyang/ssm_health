package com.itheima.dao;

import com.itheima.pojo.Role;

import java.util.Set;

public interface RoleDao {
    /**
     * 通过用户id获取角色
     * @param userId
     * @return
     */
    Set<Role> findByUserId(Integer userId);
}
