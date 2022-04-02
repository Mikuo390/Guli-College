package com.yjh.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yjh.eduservice.entity.EduSubject;
import com.yjh.eduservice.entity.excel.SubjectData;
import com.yjh.eduservice.entity.subject.OneSubject;
import com.yjh.eduservice.entity.subject.TwoSubject;
import com.yjh.eduservice.listener.SubjectExcelListener;
import com.yjh.eduservice.mapper.EduSubjectMapper;
import com.yjh.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Mikuo
 * @since 2022-03-13
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 添加课程分类
     * @param file
     */
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取所有课程
     * @return
     */
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //查询出所有一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper();
        wrapperOne.eq("parent_id",0);
        wrapperOne.orderByAsc("sort","id");

        List<EduSubject> oneSubjects = baseMapper.selectList(wrapperOne);
//        this.list(wrapperOne);//这种写法也可以

        //查询出所有二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper();
        wrapperTwo.ne("parent_id",0);
        wrapperTwo.orderByAsc("sort","id");

        List<EduSubject> twoSubjects = baseMapper.selectList(wrapperTwo);

        //创建list集合,存储最终数据
        List<OneSubject> finalSubjectList = new ArrayList<>();
        //封装一级分类
        for (int i = 0; i < oneSubjects.size(); i++) {
            //得到oneSubjectList每个eduSubject对象
            EduSubject eduSubject = oneSubjects.get(i);

            //把eduSubject里面值获取出来，放到OneSubject对象里面
            //多个OneSubject放到finalSubjectList里面
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());
            //将eduSubject完全copy到oneSubject
            BeanUtils.copyProperties(eduSubject,oneSubject);
            finalSubjectList.add(oneSubject);

            //封装二级分类
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            for (int m = 0; m < twoSubjects.size(); m++) {
                EduSubject tSubject = twoSubjects.get(m);
                //判断parentId和一级分类是否一样
                if(tSubject.getParentId().equals(eduSubject.getId())){
                    //把tSubject值复制到TowSubject里面
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject,twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }

            //把一级下面所有的二级分类放到一级分类
            oneSubject.setChildren(twoFinalSubjectList);
        }
        return finalSubjectList;
    }
}
