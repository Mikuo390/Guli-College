package com.yjh.staservice.client;

import com.yjh.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    @ApiOperation("查询某一天的注册人数")
    @GetMapping("/eduUcenter/member/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day);
}
