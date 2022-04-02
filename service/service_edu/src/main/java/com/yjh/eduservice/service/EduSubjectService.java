package com.yjh.eduservice.service;

import com.yjh.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yjh.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Mikuo
 * @since 2022-03-13
 */
public interface EduSubjectService extends IService<EduSubject> {
    /**
     * 添加课程分类
     * @param file
     */
    void saveSubject(MultipartFile file,EduSubjectService subjectService);

    /**
     * 获取所有课程
     * @return
     */
    List<OneSubject> getAllOneTwoSubject();
}
