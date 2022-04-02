package com.yjh.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjh.commonutils.R;
import com.yjh.educms.entity.CrmBanner;
import com.yjh.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author Mikuo
 * @since 2022-03-18
 */
@RestController
@RequestMapping("/eduCms/bannerAdmin")
@CrossOrigin
@Api(description = "首页Banner后台")
public class BannerAdminController {

    @Autowired
    private CrmBannerService crmBannerService;

    //分页查询
    @ApiOperation("分页查询Banner")
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit){
        Page<CrmBanner> pageBanner = new Page<>(page,limit);
        crmBannerService.page(pageBanner,null);

        return R.ok().data("items",pageBanner.getRecords()).data("total",pageBanner.getTotal());
    }

    @ApiOperation("添加banner")
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        crmBannerService.save(crmBanner);
        return R.ok();
    }

    @ApiOperation("修改Banner")
    @PutMapping("updateBanner")
    public R updateById(@RequestBody CrmBanner crmBanner) {
        crmBannerService.updateById(crmBanner);
        return R.ok();
    }

    @ApiOperation("删除Banner")
    @DeleteMapping("removeBanner/{id}")
    public R remove(@PathVariable String id) {
        crmBannerService.removeById(id);
        return R.ok();
    }

}

