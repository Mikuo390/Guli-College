package com.yjh.eduservice.controller;


import com.yjh.commonutils.R;
import com.yjh.eduservice.client.VodClient;
import com.yjh.eduservice.entity.EduVideo;
import com.yjh.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author Mikuo
 * @since 2022-03-14
 */
@RestController
@RequestMapping("/eduService/video")
@Api(description = "管理小节")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;

    //添加小节
    @ApiOperation("添加小节")
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }

    //删除小节
    @ApiOperation("删除小节")
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
        //根据小节id获取视频id,调用方法实现视频删除
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();

        if(!StringUtils.isEmpty(videoSourceId)){
            //根据视频id,远程调用实现视频删除
            System.out.println("删除了");
            vodClient.removeAlyVideo(videoSourceId);
        }
        videoService.removeById(id);
        return R.ok().data("video",eduVideo);
    }

    //修改小节
    @ApiOperation("修改小节")
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return R.ok();
    }

    //获得小节
    @ApiOperation("获得小节")
    @GetMapping("getVideo/{id}")
    public R getVideo(@PathVariable String id){

        EduVideo eduVideo = videoService.getById(id);
        return R.ok().data("video",eduVideo);
    }
}

