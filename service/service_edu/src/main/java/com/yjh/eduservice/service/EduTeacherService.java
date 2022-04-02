package com.yjh.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjh.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-03-10
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 分页查询讲师方法
     * @param pageTeacher
     * @return
     */
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);

//    void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery);

}
