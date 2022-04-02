package com.yjh.eduservice.service;

import com.yjh.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yjh.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Mikuo
 * @since 2022-03-14
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * 根据id获取章节
     * @param courseId
     * @return
     */
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    /**
     * 根据id删除章节
     * @param chapterId
     */
    boolean deleteChapter(String chapterId);

    /**
     * 根据课程id删章节
     * @param courseId
     */
    void removeChapterByCourseId(String courseId);
}
