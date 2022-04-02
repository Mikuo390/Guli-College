package com.yjh.educenter.mapper;

import com.yjh.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author Mikuo
 * @since 2022-03-28
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {
    /**
     * 查询某一天的注册人数
     * @param day
     * @return
     */
    Integer countRegisterDay(String day);
}
