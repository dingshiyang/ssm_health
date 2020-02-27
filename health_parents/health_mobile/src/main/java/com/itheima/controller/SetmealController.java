package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.itheima.constant.MessageConstant.*;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;
    /**
     * 获取所有套餐数据
     * @return
     */
    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){
        try{
           List<Setmeal> list = setmealService.getSetmeal();
            return new Result(true,GET_SETMEAL_LIST_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,GET_SETMEAL_LIST_FAIL);
        }
    }

    /**
     * 获取套餐详情
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try{
           Setmeal setmeal = setmealService.findBySetmealId(id);
           return new Result(true,QUERY_SETMEAL_SUCCESS,setmeal);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,QUERY_SETMEAL_FAIL);
        }
    }
}
