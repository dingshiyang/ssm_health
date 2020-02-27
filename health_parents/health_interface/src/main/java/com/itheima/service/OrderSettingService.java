package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * 预约设置
 * @author dsy
 */
public interface OrderSettingService {
    /**
     * 批量添加数据
     * @param list
     */
    void add(List<OrderSetting> list);

    /**
     * 查询数据返回到页面
     * @param date
     * @return
     */
    List<Map> find(String date);

    /**
     * 编辑预约设置
     * @param orderSetting
     */
    void editNumberByDate(OrderSetting orderSetting);
}
