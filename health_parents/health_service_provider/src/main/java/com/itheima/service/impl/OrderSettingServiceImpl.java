package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预约设置服务
 * @author dsy
 */
@Service(interfaceClass =OrderSettingService.class )
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Override
    public void add(List<OrderSetting> list) {
        //1.判断
        if(list!=null && list.size() > 0){
            for (OrderSetting orderSetting : list) {
                //判断当前日期是否已经被预约
                Long num = orderSettingDao.findCount(orderSetting.getOrderDate());
                if(num>0){
                    //说明当前日期已经被预约，执行修改操作
                    orderSettingDao.updateByOrderDate(orderSetting);
                }else{
                    //说明当前日期未被预约，执行添加操作
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    @Override
    public List<Map> find(String date) {
        String start=date+"-1";
        String end=date+"-31";
        Map<String,String> map = new HashMap<>();
        map.put("start",start);
        map.put("end",end);
       List<OrderSetting> list = orderSettingDao.find(map);
       List<Map> result = new ArrayList<>();
       if(list!=null && list.size()>0){
           for (OrderSetting orderSetting : list) {
             Map<String,Object> m = new HashMap<>();
               //获取日期数字
             m.put("date",orderSetting.getOrderDate().getDate());
             m.put("number",orderSetting.getNumber());
             m.put("reservations",orderSetting.getReservations());
             result.add(m);
           }
       }
        return result;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        //判断要设置的日期有没有数据
       Long num = orderSettingDao.findCount(orderSetting.getOrderDate());
       if(num>0){
           //说明有数据，执行修改操作
           orderSettingDao.updateByOrderDate(orderSetting);
       }else{
           //没有数据，执行插入操作
           orderSettingDao.add(orderSetting);
       }
    }
}
