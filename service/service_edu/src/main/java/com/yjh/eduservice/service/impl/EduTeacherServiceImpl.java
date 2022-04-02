package com.yjh.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjh.eduservice.entity.EduTeacher;
import com.yjh.eduservice.mapper.EduTeacherMapper;
import com.yjh.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-10
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {
    /**
     * 分页查询讲师方法
     * @param pageTeacher
     * @return
     */
    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //把分页数据封装到pageTeacher对象
        baseMapper.selectPage(pageTeacher,wrapper);

        List<EduTeacher> records = pageTeacher.getRecords();
        long current = pageTeacher.getCurrent();
        long pages = pageTeacher.getPages();
        long size = pageTeacher.getSize();
        long total = pageTeacher.getTotal();

        boolean hasNext = pageTeacher.hasNext();
        boolean hasPrevious = pageTeacher.hasPrevious();

        Map<String,Object> map = new HashMap<>();
        map.put("records",records);
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

//    @Override
//    public void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery) {
//        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
//        queryWrapper.orderByAsc("sort");
//
//        if (teacherQuery == null){
//            baseMapper.selectPage(pageParam, queryWrapper);
//            return;
//        }
//
//        String name = teacherQuery.getName();
//        Integer level = teacherQuery.getLevel();
//        String begin = teacherQuery.getBegin();
//        String end = teacherQuery.getEnd();
//
//        if (!StringUtils.isEmpty(name)) {
//            queryWrapper.like("name", name);
//        }
//
//        if (!StringUtils.isEmpty(level) ) {
//            queryWrapper.eq("level", level);
//        }
//
//        if (!StringUtils.isEmpty(begin)) {
//            queryWrapper.ge("gmt_create", begin);
//        }
//
//        if (!StringUtils.isEmpty(end)) {
//            queryWrapper.le("gmt_create", end);
//        }
//
//        baseMapper.selectPage(pageParam, queryWrapper);
//    }
}
