package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

import static com.itheima.constant.MessageConstant.*;
import static com.itheima.constant.RedisConstant.SETMEAL_PIC_RESOURCES;

/**
 *体检套餐管理
 * @author dsy
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private SetmealService setmealService;
    /**
     * 上传文件
     * @param imgFile
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
        String originalFilename = imgFile.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String substring = originalFilename.substring(index - 1);
        String fileName = UUID.randomUUID().toString()+substring;
        try {
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            jedisPool.getResource().sadd(SETMEAL_PIC_RESOURCES,fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false,PIC_UPLOAD_FAIL);
        }
        return new Result(true,PIC_UPLOAD_SUCCESS,fileName);
    }

    /**
     * 添加套餐数据
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @RequestMapping("/save")
    public Result save(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
        try {
            setmealService.save(setmeal,checkgroupIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,ADD_SETMEAL_FAIL);
        }
        return new Result(true,ADD_SETMEAL_SUCCESS);
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
       PageResult pageResult= setmealService.findPage(queryPageBean);
       return pageResult;
    }

    /**
     * 回显套餐数据
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Setmeal setmeal = setmealService.findById(id);
            return new Result(true,QUERY_SETMEAL_SUCCESS,setmeal);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,QUERY_SETMEAL_FAIL);
        }
    }

    /**
     * 查询检查组与套餐相关联的项
     * @param id
     * @return
     */
    @RequestMapping("/findCheckGroupAndSetmeal")
    public Result findCheckGroupAndSetmeal(Integer id){
        try{
         Integer[] arr = setmealService.findCheckGroupAndSetmeal(id);
          return new Result(true,QUERY_CHECKGROUP_SUCCESS,arr);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,QUERY_CHECKGROUP_FAIL);
        }
    }

    /**
     * 编辑套餐
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        try{
            setmealService.update(setmeal,checkgroupIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"修改套餐失败");
        }
        return new Result(true,"修改套餐成功");
    }
}
