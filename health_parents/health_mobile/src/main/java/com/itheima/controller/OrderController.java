package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import com.itheima.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

import static com.itheima.utils.SMSUtils.ORDER_NOTICE;

/**
 * 体检预约处理
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private OrderService orderService;

    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map) {
        //判断验证码是否正确，从redis中取出存入的验证码
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
        String code = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        //与用户输入的进行比较
        if (code != null && validateCode != null && code.equals(validateCode)) {
            //如果成功，调用服务层完成业务处理
            Result result = null;
            try {
                //设置预约类型 包括（电话预约，微信预约）
                map.put("orderType", Order.ORDERTYPE_WEIXIN);
                result = orderService.submit(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (result.isFlag()) {
                //预约成功，为用户发送短信
                try {
                    SMSUtils.sendShortMessage(ORDER_NOTICE, telephone, (String) map.get("orderDate"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return result;
        } else {
            //如果不成功，返回结果给页面，让用户重新输入数据
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
    }
     @RequestMapping("/findById")
     public Result findById(Integer id){
        try{
           Map map = orderService.findById(id);
           return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
     }
}
