package com.yjh.educenter.service;

import com.yjh.educenter.entity.UcenterMember;
import com.yjh.educenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Mikuo
 * @since 2022-03-28
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    /**
     * 登录
     * @param member
     * @return
     */
    String login(UcenterMember member);

    /**
     * 注册
     * @param registerVo
     */
    void register(RegisterVo registerVo);

    /**
     * 根据微信id查询
     * @param openid
     * @return
     */
    UcenterMember getByOpenid(String openid);

    /**
     * 查询某一天的注册人数
     * @param day
     * @return
     */
    Integer countRegisterDay(String day);
}
