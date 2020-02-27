package com.itheima.dao;

import com.itheima.pojo.Permission;

import java.util.Set;

public interface PermissionDao {
    /**
     * 通过角色id获取权限表
     * @param roleId
     * @return
     */
    Set<Permission> findByRoleId(Integer roleId);
}
