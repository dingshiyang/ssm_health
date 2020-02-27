package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {

     void add(CheckItem checkItem);

     Page<CheckItem> findByContion(String queryString);

     Long findByCheckitemId(Integer id);

     void delete(Integer id);

     CheckItem findById(Integer id);

     void update(CheckItem checkItem);

    List<CheckItem> findAll();

    Long findByName(String name);

    Long findByCode(String code);
}
