package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;

import java.util.List;

/**
 * 检查项服务
 * @author dsy
 */
public interface CheckItemService {
    /**
     * 添加检查项数据
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 根据条件查询数据，完成分页
     * @param queryPageBean
     * @return
     */
    PageResult findByContion(QueryPageBean queryPageBean);

    /**
     * 删除数据
     * @param id
     */
    void delete(Integer id);

    /**
     * 通过id查询数据并回显在页面
     * @param id
     * @return
     */
    CheckItem findById(Integer id);

    /**
     * 修改数据
     * @param checkItem
     */
    void update(CheckItem checkItem);

    /**
     * 查询所有检查项数据
     * @return
     */
    List<CheckItem> findAll();
}
