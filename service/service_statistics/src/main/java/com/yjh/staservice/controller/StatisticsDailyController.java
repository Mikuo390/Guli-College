package com.yjh.staservice.controller;


import com.yjh.commonutils.R;
import com.yjh.staservice.service.StatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author Mikuo
 * @since 2022-03-31
 */
@RestController
@RequestMapping("/staService/sta")
@CrossOrigin
@Api(description = "统计管理")
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService staService;

    @ApiOperation("统计某一天的注册人数")
    @PostMapping("registerCount/{day}")
    public R registerCount(@PathVariable String day){
        staService.registerCount(day);
        return R.ok();
    }

    @ApiOperation("图标显示")
    @GetMapping("showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,@PathVariable String begin,@PathVariable String end){
        Map<String,Object> map = staService.getShowData(type,begin,end);
        return R.ok().data(map);
    }
}

