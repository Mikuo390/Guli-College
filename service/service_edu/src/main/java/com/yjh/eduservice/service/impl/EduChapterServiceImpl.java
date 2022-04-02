package com.yjh.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yjh.eduservice.entity.EduChapter;
import com.yjh.eduservice.entity.EduVideo;
import com.yjh.eduservice.entity.chapter.ChapterVo;
import com.yjh.eduservice.entity.chapter.VideoVo;
import com.yjh.eduservice.mapper.EduChapterMapper;
import com.yjh.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjh.eduservice.service.EduVideoService;
import com.yjh.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Mikuo
 * @since 2022-03-14
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    //注入小节的service
    @Autowired
    private EduVideoService videoService;

    /**
     * 根据id获取章节
     * @param courseId
     * @return
     */
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //1.根据课程id查询课程里面所有的章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        //2.根据小节内的课程id或章节id查询小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);

        //创建list集合
        List<ChapterVo> finalList = new ArrayList<>();

        //3.遍历章节list集合进行封装
        //遍历查询章节的list集合
        for (int i = 0; i < eduChapterList.size(); i++) {
            //每个章节
            EduChapter eduChapter = eduChapterList.get(i);
            //将eduChapter对象复制到ChapterVo里面
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            //吧chapterVo放到list里面
            finalList.add(chapterVo);

            //创建集合用于封装章节的小节
            List<VideoVo> videoVoList = new ArrayList<>();
            //4.遍历查询小节list集合进行封装
            for (int j = 0; j < eduVideoList.size(); j++) {
                //每个小节
                EduVideo eduVideo = eduVideoList.get(j);
                //判断:属于小节的chapterid和章节id是否一样
                if(eduVideo.getChapterId().equals(eduChapter.getId())){
                    //进行封装
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    //放到小节集合中
                    videoVoList.add(videoVo);
                }
            }

            //封装之后的小节list集合放到章节对象
            chapterVo.setChildren(videoVoList);
        }
        return finalList;
    }

    /**
     * 根据id删除章节
     * @param chapterId
     */
    @Override
    public boolean deleteChapter(String chapterId) {
        //根据章节id查询小节表,如果查询数据,不进行删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        //判断
        if(count >0){
            throw new GuliException(20001,"不能删除!");
        }else{
            //删除章节
            int result = baseMapper.deleteById(chapterId);
            return result>0;
        }

    }

    /**
     * 根据课程id删章节
     * @param courseId
     */
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
