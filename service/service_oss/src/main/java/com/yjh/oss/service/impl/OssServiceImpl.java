package com.yjh.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.yjh.oss.service.OssService;
import com.yjh.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    /**
     * 上传头像到oss
     * @param file
     * @return
     */
    @Override
    public String uploadFileAvatar(MultipartFile file) {

        //获取阿里云存储相关常量
        String endPoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
//        String fileHost = ConstantPropertiesUtils.FILE_HOST;

        //上传文件流
        try (InputStream inputStream = file.getInputStream()) {
            //创建OSS实例
            OSS ossClient = new OSSClientBuilder().build(endPoint,accessKeyId,accessKeySecret);

            //获取文件名称
            String fileName = file.getOriginalFilename();

            //在文件名称里面添加随机唯一的值
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            fileName = uuid+fileName;

            //把文件按照日期进行分类
            //获取当前日期
            String datePath = new DateTime().toString("yyyy/MM/dd");
            //拼接
            fileName = datePath+"/"+fileName;

            //调用oss方法实现上传
            //第一个参数 bucket名称
            //第二个参数 上传到oss文件路径和名称 /aa/bb/1.jpg
            ossClient.putObject(bucketName, fileName, inputStream);

            // 关闭OSSClient。
            String fileUrl = "https://" + bucketName +"." + endPoint + "/" + fileName;
            ossClient.shutdown();
            //把上传之后文件路径进行返回
            return fileUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
