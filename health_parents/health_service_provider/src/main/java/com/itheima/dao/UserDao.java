package com.itheima.dao;

import com.itheima.pojo.User;

public interface UserDao {
    /**
     * 通过用户名查询用户
     * @param username
     * @return
     */
    User findByUsername(String username);
}
