package com.itheima.service;

import java.util.Map;

/**
 * 运营商服务
 * @author dsy
 */
public interface ReportService {
    /**
     *获取运营统计数据
     * @return
     */
    Map<String, Object> getBusinessReportData() throws Exception;
}
