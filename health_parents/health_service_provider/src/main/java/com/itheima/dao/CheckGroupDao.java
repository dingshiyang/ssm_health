package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;

import java.util.List;
import java.util.Map;

/**
 * 检查组
 * @author dsy
 */
public interface CheckGroupDao {
    /**
     * 添加数据
     * @param checkGroup
     */
    void add(CheckGroup checkGroup);

    /**
     * 往检查项和检查组关联的表中插入数据
     * @param map
     */
    void setCheckgroupAndCheckitem(Map map);

    /**
     * 通过条件查询页码
     * @param queryString
     * @return
     */
    Page<CheckGroup> findByContion(String queryString);

    /**
     * 删除数据
     * @param id
     */
    void delete(Integer id);

    /**
     * 查询检查组与检查项相关联的表，查询有没有数据，如果有数据说明两张表相关联
     * @param id
     * @return
     */
    Long deleteByCheckGroupId(Integer id);

    /**
     * 回显数据
     * @param id
     * @return
     */
    CheckGroup findById(Integer id);

    /**
     * 回显检查项的集合
     * @param id
     * @return
     */
    List<Integer> findCheckGroupAndCheckItem(Integer id);

    /**
     * 修改数据
     * @param checkGroup
     */
    void update(CheckGroup checkGroup);

    /**
     * 删除检查组与检查项关联表中的数据
     * @param id
     */
    void deleteAssocication(Integer id);

    /**
     * 查询检查组中所有的数据，显示在套餐中
     * @return
     */
    List<CheckGroup> findAll();

    /**
     * 查询检查组与套餐相关联的表，查询有没有数据，如果有数据说明两张表相关联
     * @param id
     * @return
     */
    Long selectSetmeal(Integer id);

    /**
     * 删除检查组与检查项相关联的表中的数据
     * @param id
     */
    void deleteCheckItemAndCheckGroup(Integer id);

}
