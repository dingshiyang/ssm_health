package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.MemberService;
import com.itheima.service.OrderService;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.itheima.pojo.Order.ORDERSTATUS_NO;

/**
 * 预约服务
 * @author dsy
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private MemberDao memberDao;
    @Override
    public Result submit(Map map) throws Exception{
       // 1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
        String orderDate = (String) map.get("orderDate");
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(DateUtils.parseString2Date(orderDate));
        if(orderSetting==null){
           //说明预约日期没有进行预约设置
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        // 2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
        int number = orderSetting.getNumber();
        int reservations = orderSetting.getReservations();
        if(reservations>=number){
            //预约日期是否已经约满,无法预约
            return new Result(false,MessageConstant.ORDER_FULL);
        }
       // 3、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约
       //通过手机号查询判断新用户有没有在之前注册过
        String telephone = (String) map.get("telephone");
        Member member=memberDao.findByTelephone(telephone);
        if(member!=null){
             //说明该用户已经注册过，判断是否在重复预约
            Integer memberId = member.getId();
           Date order_Date = DateUtils.parseString2Date(orderDate);
            String setmealId = (String) map.get("setmealId");
            Order order = new Order(memberId,order_Date,Integer.parseInt(setmealId));
           List<Order> list = orderDao.findByContion(order);
           if(list!=null && list.size()>0){
               return new Result(false,MessageConstant.HAS_ORDERED);
           }
        }else{
            // 4、检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注册并进行预约
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setName((String) map.get("name"));
            member.setIdCard((String) map.get("idCard"));
            member.setSex((String) map.get("sex"));
            member.setRegTime((Date) map.get(orderDate));
            memberDao.add(member);
        }
        // 5、预约成功，将数据存入预约表
        Order order = new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(DateUtils.parseString2Date(orderDate));
        order.setOrderType((String) map.get("orderType"));
        order.setOrderStatus(ORDERSTATUS_NO);
        order.setSetmealId(Integer.parseInt((String) map.get("setmealId")));
        orderDao.save(order);
         //更新当日的已预约人数
        orderSetting.setReservations(orderSetting.getReservations()+1);
        orderSettingDao.editReservationsByOrderDate(orderSetting);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order.getId());
    }

    @Override
    public Map findById(Integer id) throws Exception{
       Map map = orderDao.findById(id);
       if(map!=null){
           Date orderDate = (Date) map.get("orderDate");
           map.put("orderDate",DateUtils.parseDate2String(orderDate));
       }
        return map;
    }
}
