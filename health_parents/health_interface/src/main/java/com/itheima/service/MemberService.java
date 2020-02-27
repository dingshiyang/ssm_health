package com.itheima.service;

import com.itheima.pojo.Member;

import java.util.List;

/**
 * 预约服务
 */
public interface MemberService {
    /**
     * 查询用户有没有注册过
     * @param telephone
     * @return
     */
    Member findByTelephone(String telephone);

    /**
     * 用手机验证码实现快速注册
     * @param member
     */
    void add(Member member);

    /**
     * 通过月份查询人数
     * @param months
     * @return
     */
    List<Integer> findMemberCountByMonths(List<String> months);
}
