package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import com.itheima.utils.HealthStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 检查项实现类
 * @author dsy
 */
@Service(interfaceClass =CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;

    /**
     * 添加数据
     * @param checkItem
     */
    @Override
    public void add(CheckItem checkItem) {
        Long num = checkItemDao.findByName(checkItem.getName());
        if(num>0){
           throw  new RuntimeException("检查项名称已存在");
        }
       Long num1 = checkItemDao.findByCode(checkItem.getCode());
        if(num1>0){
           throw new RuntimeException("检查项编码已存在");
        }
            checkItemDao.add(checkItem);
    }

    /**
     * 查询页码
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findByContion(QueryPageBean queryPageBean) {
        //做容错性处理
        Integer currentPage = queryPageBean.getCurrentPage();
        if(null == currentPage || currentPage.intValue()<=0){
            currentPage=1;
        }
        Integer pageSize = queryPageBean.getPageSize();
        if(null == pageSize || pageSize.intValue()<=0){
            pageSize=10;
        }
        String queryString = queryPageBean.getQueryString();
        //当查询条件不为空时，有可能会出现"  hello  "这种前后出现空格现象
        //queryString.trim()的作用就是去掉字符串前后的空格，但不能去掉字符串中间的空格
        if(HealthStringUtils.isNotEmpty(queryString)){
          queryString=queryString.trim();
        }else{
            queryString="";
        }
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckItem> page = checkItemDao.findByContion("%"+queryString+"%");
        long total = page.getTotal();
        List<CheckItem> rows = page.getResult();
        return new PageResult(total,rows);
    }

    /**
     * 根据id删除数据
     * @param id
     */
    @Override
    public void delete(Integer id) {

        //判断检查项有没有被检查组关联
        Long aLong = checkItemDao.findByCheckitemId(id);
        if(aLong>0){
            new RuntimeException();
        }
        checkItemDao.delete(id);
    }

    /**
     * 数据回显
     * @param id
     * @return
     */
    @Override
    public CheckItem findById(Integer id) {
        CheckItem item = checkItemDao.findById(id);
        return item;
    }

    /**
     * 修改数据
     * @param checkItem
     */
    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }

    /**
     * 查询所有数据
     * @return
     */
    @Override
    public List<CheckItem> findAll() {
       List<CheckItem> list = checkItemDao.findAll();
        return list;
    }
}
