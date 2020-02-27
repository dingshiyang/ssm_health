package com.itheima.dao;

import com.itheima.pojo.Member;

/**
 * 预约服务
 * @author dsy
 */
public interface MemberDao {

    /**
     * 通过手机号查询该用户有没有在本院注册过
     * @param telephone
     * @return
     */
    Member findByTelephone(String telephone);

    /**
     * 往t_member中添加数据，为用户一次注册
     * @param member
     */
    void add(Member member);

    /**
     * 通过月份查询人数
     * @param date
     * @return
     */
    Integer findMemberCountByMonths(String date);

    /**
     * 查询今日新增会员数
     * @param reportDate
     * @return
     */
    Integer findMemberCountByDate(String reportDate);

    /**
     * 查询总会员数
     * @return
     */
    Integer findMemberTotalCount();

    /**
     * 查询本周新增会员数
     * @param thisWeekMonday
     * @return
     */
    Integer findMemberCountAfterDate(String thisWeekMonday);
}
