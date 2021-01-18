package com.atguigu.service;

import com.atguigu.pojo.Member;

import java.util.List;
import java.util.Map;

public interface MemberService {
    Member findByIdTelephone(String telephone);

    void add(Member member);

    List<Integer> findMemberReport(List<String> map);
}
