package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.MemberDao;
import com.atguigu.pojo.Member;
import com.atguigu.service.MemberService;
import com.atguigu.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    MemberDao memberDao;

    @Override
    public List<Integer> findMemberReport(List<String> memberlist) {
        List<Integer> list=new ArrayList<>();
        for (String month:memberlist){
            String lastDateofMonth= DateUtils.getLastDayOfMonth(month);
            Integer integer=memberDao.findMemberReport(lastDateofMonth);
            list.add(integer);
        }
        return list;
    }

    @Override
    public Member findByIdTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        memberDao.add(member);
    }
}
