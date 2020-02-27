package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;

import java.util.List;

/**
 * 检查组服务
 * @author dsy
 */
public interface CheckGroupService {
    /**
     * 添加检查组数据
     * @param checkGroup
     * @param checkitemIds
     */
    void add(CheckGroup checkGroup,Integer[] checkitemIds);

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    PageResult findPage(QueryPageBean queryPageBean);

    /**
     * 删除检查组数据
     * @param id
     */
    void delete(Integer id);

    /**
     * 通过id查询检查组数据，回显在页面
     * @param id
     * @return
     */
    CheckGroup  findById(Integer id);

    /**
     * 根据t_checkgroup_checkitem表查询检查组和检查项
     * @param id
     * @return
     */
    List<Integer> findCheckGroupAndCheckItem(Integer id);

    /**
     * 修改检查组数据
     * @param checkGroup
     * @param checkitemIds
     */
    void update(CheckGroup checkGroup,Integer[] checkitemIds);

    /**
     * 查询所有检查组对象
     * @return
     */
    List<CheckGroup> findAll();
}
