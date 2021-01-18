package com.atguigu.dao;

import com.atguigu.pojo.Member;

public interface MemberDao {
    void add(Member member);

    Member findByTelephone(String telephone);

    Integer findMemberReport(String lastDateofMonth);

    int getTodayNewMember(String today);

    int getTotalMember();

    int getThisweekAndMonthNewMenber(String weekMonday);
}
