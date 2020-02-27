package com.itheima.service;

import com.itheima.entity.Result;

import java.util.Map;

/**
 * 体检预约处理
 * @author dsy
 */
public interface OrderService {
    /**
     * 提交预约
     * @param map
     * @return
     * @exception
     */
    Result submit(Map map) throws Exception;

    /**
     * 查询预约信息是否成功
     * @param id
     * @return
     */
    Map findById(Integer id) throws Exception;
}
