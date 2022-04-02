package com.yjh.eduservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

//@EnableSwagger2
@ComponentScan(basePackages = {"com.yjh"}) //开启扫描配置类,扫描其他类
@SpringBootApplication
@MapperScan("com.yjh.educenter.mapper")
//@EnableDiscoveryClient
@EnableFeignClients //开启Feign功能
//@EntityScan(basePackages = "com.yjh.eduservice")
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class);
    }
}
