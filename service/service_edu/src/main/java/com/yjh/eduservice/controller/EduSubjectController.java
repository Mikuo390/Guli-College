package com.yjh.eduservice.controller;


import com.yjh.commonutils.R;
import com.yjh.eduservice.entity.subject.OneSubject;
import com.yjh.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author Mikuo
 * @since 2022-03-13
 */
@RestController
@RequestMapping("/eduService/subject")
@CrossOrigin
@Api(description="课程管理")
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    //添加课程分类
    @ApiOperation("添加课程分类")
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        //上传过来的excel文件
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }


    //课程分类
    @ApiOperation("获得课程分类")
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        //list集合泛型是一级分类
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }

}

