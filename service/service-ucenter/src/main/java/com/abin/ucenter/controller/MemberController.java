package com.abin.ucenter.controller;


import com.abin.ucenter.entity.Member;
import com.abin.ucenter.entity.vo.RegisterVo;
import com.abin.ucenter.service.MemberService;
import com.abin.utils.JwtUtils;
import com.abin.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 大冰
 * @since 2021-01-28
 */
@CrossOrigin
@RestController
@RequestMapping("/ucenter/member")
public class MemberController {

    @Autowired
    private MemberService memberService;


    //登录方法  返回token进行单点登录
    @PostMapping("/login")
    public R login(@RequestBody Member member) {

        String token = memberService.login(member);
        // 返回的token值 进行单点登录
        return R.ok().data("token", token);
    }


    // 根据token获取用户信息
    @GetMapping("/getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        // 调用jwt工具类方法 根据request对象头信息 返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        // 根据用户id查出用户信息
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", memberId);
        Member member = memberService.getOne(wrapper);
//        Member member = memberService.getById(memberId);
        return R.ok().data("userInfo", member);
    }

    // 微信登录 根据token获取用户信息
    @GetMapping("/getWxMember")
    public R getWxMember(HttpServletRequest request) {
        // 调用jwt工具类方法 根据request对象头信息 返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
//        System.out.println("memberid：是" + memberId);
        // 根据用户id查出用户信息
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("id", memberId);
        Member member = memberService.getOne(wrapper);
//        Member member = memberService.getById(memberId);
        return R.ok().data("userInfo", member);
    }

    // 注册方法
    @PostMapping("/register")
    public R register(@RequestBody RegisterVo registerVo) {

        memberService.register(registerVo);

        return R.ok();
    }


}