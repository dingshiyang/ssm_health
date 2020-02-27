package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.itheima.constant.MessageConstant.*;

/**
 * 检查项管理
 * @author dsy
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
   @Reference
    private CheckItemService checkItemService;

    /**
     * 添加数据
     * @param checkItem
     * @return
     */
    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        try{
            checkItemService.add(checkItem);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,ADD_CHECKITEM_FAIL,e.getMessage());
        }
        return new Result(true,ADD_CHECKITEM_SUCCESS);
    }

    /**
     * 查询页码
     * @param queryPageBean
     * @return
     */
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = checkItemService.findByContion(queryPageBean);
        return pageResult;
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            checkItemService.delete(id);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,DELETE_CHECKITEM_FAIL);
        }
        return new Result(true,DELETE_CHECKITEM_SUCCESS);
    }

    /**
     * 数据回显
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            CheckItem checkItem = checkItemService.findById(id);
            return new Result(true,QUERY_CHECKGROUP_SUCCESS,checkItem);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,QUERY_CHECKGROUP_FAIL);
        }
    }

    /**
     * 修改数据
     * @param checkItem
     * @return
     */
    @PreAuthorize("hasAuthority('CHECKITEM_EDIT')")
   @RequestMapping("/update")
    public Result update(@RequestBody CheckItem checkItem){
        try {
            checkItemService.update(checkItem);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,EDIT_CHECKITEM_FAIL);
        }
        return new Result(true,EDIT_CHECKITEM_SUCCESS);
    }

    /**
     * 查询所有数据
     * @return
     */
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    @RequestMapping("/findAll")
    public Result findAll(){
        try {
            List<CheckItem> list = checkItemService.findAll();
            return new Result(true,QUERY_CHECKITEM_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,QUERY_CHECKITEM_FAIL);
        }
    }
}
