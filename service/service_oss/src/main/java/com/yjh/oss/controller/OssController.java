package com.yjh.oss.controller;

import com.yjh.commonutils.R;
import com.yjh.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduOss/fileOss")
@CrossOrigin
@Api(description="Oss")
//@PropertySource(value="classpath:application.yml")
public class OssController {

    @Autowired
    private OssService ossService;

    //上传头像的方法
    @ApiOperation(value = "上传头像方法")
    @PostMapping
    public R uploadOssFile(MultipartFile file){
        //获取上传文件 MultipartFile
        //返回上传到oss路径
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }

}
