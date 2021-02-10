package com.abin.ucenter.service.impl;


import com.abin.servicebase.exceptionhandler.BizarreException;
import com.abin.ucenter.config.StringRedisTemplate;
import com.abin.ucenter.entity.Member;
import com.abin.ucenter.entity.vo.RegisterVo;
import com.abin.ucenter.mapper.MemberMapper;
import com.abin.ucenter.service.MemberService;
import com.abin.utils.JwtUtils;
import com.abin.utils.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author 大冰
 * @since 2021-01-28
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

//    @Resource(name = "stringRedisTemplate")
//    private StringRedisTemplate stringRedisTemplate;

    // 登录验证
    @Override
    public String login(Member member) {

        // 前台传过来的手机号
        String mobile = member.getMobile();
        // 前台传来的密码
        String password = member.getPassword();

        //手机号和密码非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new BizarreException(20001, "手机号或密码为空");
        }

        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        // 通过手机号查询出来的用户信息
        Member mobileMember = baseMapper.selectOne(wrapper);

        if (mobileMember == null) { //没有这个用户
            throw new BizarreException(20001, "用户名不存在");
        }
        // 获取的密码进行MD5加密 跟数据库进行比较
        if (!MD5.encrypt(password).equals(mobileMember.getPassword())) {
            throw new BizarreException(20001, "账号或密码错误");
        }
        // 判断用户是否被禁用
        if (mobileMember.getIsDisabled()) {
            throw new BizarreException(20001, "用户被禁用");
        }
        // 上面判断都不成立表示登录成功
        // 通过用户手机号和密码生成jwtToken 返回
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getMobile(), mobileMember.getPassword());

        return jwtToken;
    }

    @Override
    public void register(RegisterVo registerVo) {

        // 注册的验证码
        String code = registerVo.getCode();
        System.out.println("前台获取code String" + code instanceof String);
        // 用户手机号
        String phoneNumber = registerVo.getMobile();
        // 用户昵称
        String nickname = registerVo.getNickname();
        // 用户密码
        String password = registerVo.getPassword();
        // 非空判断
        if (StringUtils.isEmpty(phoneNumber) || StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)) {
            throw new BizarreException(20001, "注册数据为空");
        }
        // 判断验证吗
        // 验证码在获取时存入redis中 redis中的key是手机号 value是验证吗 从redis中取出进行比较
        String redisCode = redisTemplate.opsForValue().get(phoneNumber);

        //System.out.println("redis中获取 String" + redisCode instanceof String);

        // 判断用户输入的验证码和redis中的验证码是否是一样
        // 不一样报错
        if (!code.equals(redisCode)) {
            throw new BizarreException(20001, "注册失败");
        }
        // 判断手机号是否注册过
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", phoneNumber);
        // 查询数据库 如果selectOne是否大于零  大于零 用户已存在
        Integer selectOne = baseMapper.selectCount(wrapper);
        // 大于零 用户已存在
        if (selectOne > 0) {
            throw new BizarreException(20001, "用户已存在");
        }

        // 上诉都不存在 表示用户没有注册过
        Member member = new Member();
        member.setMobile(phoneNumber);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false); //用户不禁用
        member.setAvatar("https://edu-shopping.oss-cn-beijing.aliyuncs.com/01.jpg"); // 设置头像
        baseMapper.insert(member);

    }

    @Override
    public Member getOpenIdMember(String openid) {

        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        Member memberOne = baseMapper.selectOne(wrapper);

        return memberOne;
    }

    //注册的方法
//    @Override
//    public void register(RegisterVo registerVo) {
//        //获取注册的数据
//        String code = registerVo.getCode(); //验证码
//        String mobile = registerVo.getMobile(); //手机号
//        String nickname = registerVo.getNickname(); //昵称
//        String password = registerVo.getPassword(); //密码
//
//        //非空判断
//        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)
//                || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)) {
//            throw new BizarreException(20001, "注册失败");
//        }
//        //判断验证码
//        //获取redis验证码
//
//        String redisCode = (String) redisTemplate.opsForValue().get(mobile);
////        redisCode = String.valueOf(redisCode);
//        if (!code.equals(redisCode)) {
//            throw new BizarreException(20001, "注册失败");
//        }
//
//        //判断手机号是否重复，表里面存在相同手机号不进行添加
//        QueryWrapper<Member> wrapper = new QueryWrapper<>();
//        wrapper.eq("mobile", mobile);
//        Integer count = baseMapper.selectCount(wrapper);
//        if (count > 0) {
//            throw new BizarreException(20001, "注册失败");
//        }
//
//        //数据添加数据库中
//        Member member = new Member();
//        member.setMobile(mobile);
//        member.setNickname(nickname);
//        member.setPassword(MD5.encrypt(password));//密码需要加密的
//        member.setIsDisabled(false);//用户不禁用
//        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
//        baseMapper.insert(member);
//    }

}
