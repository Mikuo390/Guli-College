package com.yjh.eduservice.mapper;

import com.yjh.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjh.eduservice.entity.frontvo.CourseWebVo;
import com.yjh.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author Mikuo
 * @since 2022-03-14
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    /**
     * 根据课程id查询课程确认信息
     * @param courseId
     * @return
     */
    public CoursePublishVo getPublishCourseInfo(String courseId);

    /**
     * 根据课程id,查询课程信息
     * @param courseId
     * @return
     */
    CourseWebVo getBaseCourseInfo(String courseId);
}
