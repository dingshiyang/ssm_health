package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealDao {
    /**
     * 添加套餐
     * @param setmeal
     */
    void save(Setmeal setmeal);

    /**
     * 添加套餐和检查组数据
     * @param map
     */
    void saveSetmealIdAndCheckGroupId(Map<String, Integer> map);

    /**
     * 按条件查询数据
     * @param queryString
     * @return
     */
    Page<Setmeal> findByContion(String queryString);

    /**
     * 获取所有套餐数据
     * @return
     */
    List<Setmeal> getSetmeal();

    /**
     * 通过查询套餐数据，回显在页面
     * @param id
     * @return
     */
    Setmeal findById(Integer id);

    /**
     * 查询检查组与套餐相关联的项
     * @param id
     * @return
     */
    Integer[] findCheckGroupAndSetmeal(Integer id);


    /**
     * 删除关联表中的数据
     * @param setmealId
     */
    void deleteSetmealAndCheckGroup(Integer setmealId);

    /**
     * 修改套餐基本信息
     * @param setmeal
     */
    void updateSetmeal(Setmeal setmeal);

    /**
     * 获取套餐详情
     * @param id
     * @return
     */
    Setmeal findBySetmealId(Integer id);

    /**
     * 统计套餐占比
     * @return
     */
    List<Map<String, Object>> findSetmealCount();
}
