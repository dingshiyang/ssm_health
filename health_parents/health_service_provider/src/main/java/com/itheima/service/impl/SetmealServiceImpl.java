package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.itheima.constant.RedisConstant.SETMEAL_PIC_DB_RESOURCES;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private JedisPool jedisPool;

    @Override
    public void save(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.save(setmeal);
        Integer id = setmeal.getId();
        for (Integer checkgroupId : checkgroupIds) {
            Map<String,Integer> map = new HashMap<>() ;
            map.put("setmealId",id);
            map.put("checkgroupId",checkgroupId);
            setmealDao.saveSetmealIdAndCheckGroupId(map);
        }
        jedisPool.getResource().sadd(SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        PageHelper.startPage(currentPage,pageSize);
       Page<Setmeal> page = setmealDao.findByContion(queryString);
        long total = page.getTotal();
        List<Setmeal> rows = page.getResult();
        return new PageResult(total,rows);
    }

    @Override
    public List<Setmeal> getSetmeal() {
      List<Setmeal> list = setmealDao.getSetmeal();
      return list;
    }

    @Override
    public Setmeal findById(Integer id) {
       Setmeal setmeal = setmealDao.findById(id);
        return setmeal;
    }

    @Override
    public Integer[] findCheckGroupAndSetmeal(Integer id) {
       Integer[] arr = setmealDao.findCheckGroupAndSetmeal(id);
        return arr;
    }

    @Override
    public void update(Setmeal setmeal, Integer[] checkgroupIds) {
            Integer setmealId= setmeal.getId();
            //修改套餐基本信息
            setmealDao.updateSetmeal(setmeal);
           //先删除关联表中的数据
           setmealDao.deleteSetmealAndCheckGroup(setmealId);
           //重新向关联表中添加数据
           for (Integer checkgroupId : checkgroupIds) {
               Map<String,Integer> map = new HashMap<>();
               map.put("checkgroupId",checkgroupId);
               map.put("setmealId",setmealId);
               setmealDao.saveSetmealIdAndCheckGroupId(map);
           }
       }

    @Override
    public Setmeal findBySetmealId(Integer id) {
       Setmeal setmeal = setmealDao.findBySetmealId(id);
        return setmeal;
    }

    @Override
    public List<Map<String, Object>> findSetmealCount() {
       return setmealDao.findSetmealCount();
    }
}
