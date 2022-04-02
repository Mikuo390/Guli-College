package com.yjh.educms.service;

import com.yjh.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author Mikuo
 * @since 2022-03-18
 */
public interface CrmBannerService extends IService<CrmBanner> {

    /**
     * 查询所有banner
     * @return
     */
    List<CrmBanner> selectAllBanner();
}
