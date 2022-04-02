package com.yjh.educms.controller;


import com.yjh.commonutils.R;
import com.yjh.educms.entity.CrmBanner;
import com.yjh.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author Mikuo
 * @since 2022-03-18
 */
@RestController
@RequestMapping("/eduCms/bannerFront")
@CrossOrigin
@Api(description = "首页Banner")
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation("查询所有banner")
    @GetMapping("getAllBanner")
    public R getAllBanner(){
        List<CrmBanner> list = bannerService.selectAllBanner();
//        System.out.println(list);
        return R.ok().data("list",list);
    }

}

