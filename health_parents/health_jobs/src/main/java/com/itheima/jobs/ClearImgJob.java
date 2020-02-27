package com.itheima.jobs;

import com.itheima.constant.RedisConstant;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * 自定义job，实现定时处理垃圾图片
 * @author dsy
 */
public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;

    /**
     * 清理图片
     */
    public void cleanImg(){
        //根据redis中存的两个set集合进行差值计算，获得垃圾图片名称集合
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        for (String s : set) {
           //从七牛云中删除图片
            QiniuUtils.deleteFileFromQiniu(s);
            //从redis中移除多余的图片
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,s);
            System.out.println("已执行");
        }
    }
}
