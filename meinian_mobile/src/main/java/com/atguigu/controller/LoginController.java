package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Member;
import com.atguigu.service.MemberService;
import javassist.bytecode.annotation.MemberValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Reference
    MemberService memberService;

    @Autowired
    JedisPool jedisPool;

    @RequestMapping("/check")
    public Result check(@RequestBody Map map, HttpServletResponse response){
        String telephone = map.get("telephone").toString();
        String code = map.get("validateCode").toString();
        String phonecode = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        if(phonecode==null||!phonecode.equals(code)){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }else {
            Member member=memberService.findByIdTelephone(telephone);
            if(member==null){
                member=new Member();
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                memberService.add(member);
            }
        }

        Cookie cook = new Cookie("cook", telephone);
        cook.setPath("/");
        cook.setMaxAge(60*60*24*30);
        response.addCookie(cook);
        return new Result(true,MessageConstant.LOGIN_SUCCESS);
    }
}
