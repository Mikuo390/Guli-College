package com.yjh.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    /**
     * 上次视频到阿里云
     * @param file
     * @return
     */
    String uploadVideoAly(MultipartFile file);

    /**
     * 批量删除阿里云视频
     * @param videoIdList
     */
    void removeMoreAlyVideo(List videoIdList);
}
