package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * 套餐服务
 * @author dsy
 */
public interface SetmealService {
    /**
     * 添加套餐数据
     * @param setmeal
     * @param checkgroupIds
     */
    void save(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 按条件查询数据
     * @param queryPageBean
     * @return
     */
    PageResult findPage(QueryPageBean queryPageBean);

    /**
     * 获取所有套餐数据
     * @return
     */
    List<Setmeal> getSetmeal();

    /**
     * 回显数据
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
     * 编辑套餐
     * @param setmeal
     * @param checkgroupIds
     */
    void update(Setmeal setmeal, Integer[] checkgroupIds);

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
