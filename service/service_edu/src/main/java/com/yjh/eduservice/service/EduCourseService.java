package com.yjh.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjh.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yjh.eduservice.entity.frontvo.CourseFrontVo;
import com.yjh.eduservice.entity.frontvo.CourseWebVo;
import com.yjh.eduservice.entity.vo.CourseInfoVo;
import com.yjh.eduservice.entity.vo.CoursePublishVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Mikuo
 * @since 2022-03-14
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 添加课程信息
     * @param courseInfoVo
     */
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据课程id查询课程基本信息
     * @param courseId
     * @return
     */
    CourseInfoVo getCourseInfo(String courseId);

    /**
     * 修改课程信息
     * @param courseInfoVo
     * @return
     */
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据课程id查询课程确认信息
     * @param id
     * @return
     */
    CoursePublishVo publishCourseInfo(String id);

    /**
     * 删除课程
     * @param courseId
     */
    void removeCourse(String courseId);

    /**
     * 条件查询课程
     * @param pageCourse
     * @param courseFrontVo
     * @return
     */
    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    /**
     * 根据课程id,查询课程信息
     * @param courseId
     * @return
     */
    CourseWebVo getBaseCourseInfo(String courseId);
}
