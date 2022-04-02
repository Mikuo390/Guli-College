package com.yjh.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.yjh.commonutils.R;
import com.yjh.servicebase.exceptionhandler.GuliException;
import com.yjh.vod.service.VodService;
import com.yjh.vod.utils.ConstantVodUtils;
import com.yjh.vod.utils.InitVodClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduVod/video")
@CrossOrigin
@Api(description = "阿里云点播")
public class VodController {
    @Autowired
    private VodService vodService;

    //上传视频到阿里云
    @ApiOperation("上传视频到阿里云")
    @PostMapping("uploadAlyiVideo")
    public R uploadAlyiVideo(MultipartFile file){
        //返回上传视频id
        String videoId = vodService.uploadVideoAly(file);
        return R.ok().data("videoId",videoId);
    }

    @ApiOperation("根据id删除视频")
    @DeleteMapping("removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id){
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建一个删除视频的Request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request设置视频Id
            request.setVideoIds(id);
            //调用初始化对象实现删除
            client.getAcsResponse(request);
            return R.ok();
        } catch (Exception e){
            e.printStackTrace();
            throw new GuliException(200001,"删除视频失败!");
        }

    }

    @ApiOperation("批量删除视频")
    @DeleteMapping("deleteBatch")
    public R deleteBatch(@ApiParam(name = "videoIdList", value = "云端视频id", required = true)
                             @RequestParam("videoIdList") List<String> videoIdList){
        vodService.removeMoreAlyVideo(videoIdList);

        return R.ok();
    }

    //根据视频id获取视频凭证
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id){
//        System.out.println(id);
//        try {
            //创建初始化对象
            DefaultAcsClient client =
                    InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建获取凭证request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

            //向request设置视频id
        request.setVideoId(id);
            try {
                GetVideoPlayAuthResponse response = client.getAcsResponse(request);
                String playAuth = response.getPlayAuth();
                return R.ok().data("playAuth",playAuth);
            }catch (Exception e){
                throw new GuliException(20001,"获取凭证失败");
            }

//        }catch (Exception e){
//            throw new GuliException(20001,"获取凭证失败");
//        }

    }
}
