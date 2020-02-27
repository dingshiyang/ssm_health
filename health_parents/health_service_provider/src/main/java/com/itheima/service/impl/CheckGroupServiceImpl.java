package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import com.itheima.utils.HealthStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     * 新增数据
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //插入数据到t_checkgroup
        checkGroupDao.add(checkGroup);
        //插入数据到t_checkgroup_checkitem
        Integer checkGroupId = checkGroup.getId();
        this.setCheckgroupAndCheckitem(checkGroupId,checkitemIds);
    }

    /**
     * 查询页码
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        if(currentPage == null || currentPage.intValue()<=0){
            currentPage=1;
        }
        Integer pageSize = queryPageBean.getPageSize();
        if(pageSize == null || pageSize.intValue()<=0){
            pageSize=10;
        }
        String queryString = queryPageBean.getQueryString();
        if(HealthStringUtils.isNotEmpty(queryString)){
            queryString=queryString.trim();
        }else{
            queryString="";
        }

        PageHelper.startPage(currentPage, pageSize);
        Page<CheckGroup> groupPage = checkGroupDao.findByContion("%"+queryString+"%");

        return new PageResult(groupPage.getTotal(), groupPage.getResult());
    }

    /**
     * 删除检查组数据
     * @param id
     */
    @Override
    public void delete(Integer id) {
        //判断套餐是否与检查组相关联
      Long num=  checkGroupDao.selectSetmeal(id);
      if(num>0){
         //套餐与检查组相关联
         throw new RuntimeException("套餐中含有检查组信息");
      }else{
          //判断检查组是否与检查项相关联，如果相关联先删除关联表中的数据
          Long num1 = checkGroupDao.deleteByCheckGroupId(id);
          if (num1 > 0) {
              //检查组与检查项相关联
              checkGroupDao.deleteCheckItemAndCheckGroup(id);
          }
          checkGroupDao.delete(id);
      }
    }

    /**
     * 回显检查组的基本信息
     * @param id
     * @return
     */
    @Override
    public CheckGroup findById(Integer id) {
        CheckGroup checkGroup = checkGroupDao.findById(id);
        return checkGroup;
    }

    /**
     * 回显与检查组相关联的检查项的id集合
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckGroupAndCheckItem(Integer id) {
        List<Integer> list = checkGroupDao.findCheckGroupAndCheckItem(id);
        return list;
    }

    /**
     * 修改数据
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        //修改检查组基本信息，操作t_checkgroup表
        checkGroupDao.update(checkGroup);
        //清理当前检查组关联的检查项
        checkGroupDao.deleteAssocication(checkGroup.getId());
        //重新建立关联关系
       Integer checkGroupId = checkGroup.getId();
        this.setCheckgroupAndCheckitem(checkGroupId,checkitemIds);
    }

    /**
     * 查询检查组所有数据
     * @return
     */
    @Override
    public List<CheckGroup> findAll() {
       List<CheckGroup> list = checkGroupDao.findAll();
        return list;
    }


    /**
     * 多表操作公共类
     * @param checkGroupId
     * @param checkitemIds
     */
    public void setCheckgroupAndCheckitem(Integer checkGroupId, Integer[] checkitemIds) {
        if (checkitemIds != null && checkitemIds.length > 0) {
            for (Integer checkitemId : checkitemIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("checkgroupId", checkGroupId);
                map.put("checkitemId", checkitemId);
                checkGroupDao.setCheckgroupAndCheckitem(map);
            }
        }
    }
}
