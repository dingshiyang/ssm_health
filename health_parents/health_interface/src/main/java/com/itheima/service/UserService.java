package com.itheima.service;

import com.itheima.pojo.User;

/**
 * 用户服务
 * @author dsy
 */
public interface UserService {
    /**
     * 根据用户名查询用户，以及关联的角色表和关联的权限表
     * @param username
     * @return
     */
    User findByUsername(String username);
}
