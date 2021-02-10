package com.abin.ucenter.service;

import com.abin.ucenter.entity.Member;
import com.abin.ucenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author 大冰
 * @since 2021-01-28
 */
public interface MemberService extends IService<Member> {

    String login(Member member);

    void register(RegisterVo registerVo);

    Member getOpenIdMember(String openid);
}
