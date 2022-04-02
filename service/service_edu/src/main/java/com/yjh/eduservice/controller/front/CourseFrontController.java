package com.yjh.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjh.commonutils.JwtUtils;
import com.yjh.commonutils.R;
import com.yjh.commonutils.ordervo.CourseWebVoOrder;
import com.yjh.eduservice.client.OrdersClient;
import com.yjh.eduservice.entity.EduCourse;
import com.yjh.eduservice.entity.chapter.ChapterVo;
import com.yjh.eduservice.entity.frontvo.CourseFrontVo;
import com.yjh.eduservice.entity.frontvo.CourseWebVo;
import com.yjh.eduservice.service.EduChapterService;
import com.yjh.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduService/courseFront")
@CrossOrigin
@Api(description = "前端课程列表")
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrdersClient ordersClient;

    @ApiOperation("条件查询带分页查询课程")
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit, @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> pageCourse = new Page<>(page,limit);
        Map<String,Object> map = courseService.getCourseFrontList(pageCourse,courseFrontVo);

        //返回分页所有数据
        return R.ok().data(map);
    }

    @ApiOperation("获取课程详情的方法")
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){
//        System.out.println(courseId);
        //根据课程id,查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);
        //根据课程id查询章节和小节
        List<ChapterVo> chapterVideoList= chapterService.getChapterVideoByCourseId(courseId);
        //根据课程id和用户id查询当前课程是否已经支付过了
        String memberId= JwtUtils.getMemberIdByJwtToken(request);
//        System.out.println("member:"+memberId);
        if(memberId==null){
            memberId = "1";
        }
        boolean buyCourse = ordersClient.isBuyCourse(courseId, memberId);
//        boolean buyCourse = true;
        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList).data("isBuy",buyCourse);
    }

    @ApiOperation("根据课程id查询课程信息")
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id){
        CourseWebVo courseInfo = courseService.getBaseCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseInfo,courseWebVoOrder);
        return courseWebVoOrder;
    }


}
