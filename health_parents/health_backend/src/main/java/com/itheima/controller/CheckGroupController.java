package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.itheima.constant.MessageConstant.*;

@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 新增数据
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
       try{
          checkGroupService.add(checkGroup,checkitemIds);
       }catch (Exception e){
           e.printStackTrace();
           return new Result(false,ADD_CHECKGROUP_FAIL);
       }
       return new Result(true,ADD_CHECKGROUP_SUCCESS);
    }

    /**
     * 页码查询
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult page = checkGroupService.findPage(queryPageBean);
        return page;
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @RequestMapping("/deleteById")
    public Result deleteById(Integer id){
        try {
            checkGroupService.delete(id);
        }catch (Exception e){
         e.printStackTrace();
         return new Result(false,DELETE_CHECKGROUP_FAIL,e.getMessage());
        }
        return new Result(true,DELETE_CHECKGROUP_SUCCESS);
    }

    /**
     * 基本信息回显
     * @param id
     * @return
     */
   @RequestMapping("/findById")
    public  Result findById(Integer id){
        try{
          CheckGroup checkGroup = checkGroupService.findById(id);
          return new Result(true,QUERY_CHECKGROUP_SUCCESS,checkGroup);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,QUERY_CHECKGROUP_FAIL);
        }
    }

    /**
     * 检查项信息回显
     * @param id
     * @return
     */
    @RequestMapping("/findCheckGroupAndCheckItem")
    public Result findCheckGroupAndCheckItem(Integer id){
        try{
           List<Integer> list = checkGroupService.findCheckGroupAndCheckItem(id);
           return new Result(true,QUERY_CHECKITEM_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,QUERY_CHECKITEM_FAIL);
        }
    }

    /**
     * 修改数据
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        try{
            checkGroupService.update(checkGroup,checkitemIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,EDIT_CHECKGROUP_FAIL);
        }
        return new Result(true,EDIT_CHECKGROUP_SUCCESS);
    }

    /**
     * 查询所有
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll(){
        try{
           List<CheckGroup> list = checkGroupService.findAll();
           return new Result(true,QUERY_CHECKGROUP_SUCCESS,list);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,QUERY_CHECKGROUP_FAIL);
        }
    }
}
