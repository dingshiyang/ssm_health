package com.itheima.dao;

import com.itheima.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 预约设置
 * @author dsy
 */
public interface OrderSettingDao {
    /**
     * 查询当前日期是否被预约
     * @param orderDate
     * @return
     */
    Long findCount(Date orderDate);

    /**
     * 修改预约设置中的人数
     * @param orderSetting
     */
    void updateByOrderDate(OrderSetting orderSetting);

    /**
     * 添加数据
     * @param orderSetting
     */
    void add(OrderSetting orderSetting);

    /**
     * 查询数据并返回前端页面
     * @param map
     * @return
     */
    List<OrderSetting> find(Map<String, String> map);

    /**
     * 通过日期查询预约日期是否已经提前进行了预约设置
     * @param orderDate
     * @return
     */
    OrderSetting findByOrderDate(Date orderDate);

    /**
     * 更新已预约的人数
     * @param orderSetting
     */
    void editReservationsByOrderDate(OrderSetting orderSetting);
}
