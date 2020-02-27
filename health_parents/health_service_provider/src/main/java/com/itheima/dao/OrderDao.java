package com.itheima.dao;

import com.itheima.pojo.Member;
import com.itheima.pojo.Order;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    /**
     * 通过条件查询预约数据
     * @param order
     * @return
     */
    List<Order> findByContion(Order order);

    /**
     * 将数据保存到订单表
     * @param order
     */
    void save(Order order);

    /**
     * 查询预约信息是否成功
     * @param id
     * @return
     */
    Map findById(Integer id);

    /**
     * 查询今日预约数
     * @param reportDate
     * @return
     */
    Integer findOrderCountByDate(String reportDate);

    /**
     * 根据日期统计到诊数
     * @param reportDate
     * @return
     */
    Integer findVisitsCountByDate(String reportDate);

    /**
     * 查询本周预约数
     * @param thisWeekMonday
     * @return
     */
    Integer findOrderCountAfterDate(String thisWeekMonday);

    /**
     * 查询本月到诊数
     * @param firstDay4ThisMonth
     * @return
     */
    Integer findVisitsCountAfterDate(String firstDay4ThisMonth);

    /**
     * 查询热门套餐
     * @return
     */
    List<Map<String, Object>> findHotSetmeal();
}
