package com.bread.oss.controller;



import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.bread.oss.adapter.impl.MinioAdapterImpl;
import com.bread.oss.entity.Result;
import com.bread.oss.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 文件控制器
 * @Author:bread
 * @Date: 2024-09-11 19:39
 */
@RestController
@CrossOrigin(origins = "http://localhost:8003", allowCredentials = "true")
public class FileController {
    @Resource
    private MinioAdapterImpl minioAdapter;

    @Resource
    private FileService fileService;

    @Value("${storage.service.type}")
    private String storageType;

    @RequestMapping("/testnacos")
    public String test2()throws Exception{

        String s=storageType;
        if(s==null||s.equals("")){
            s="null";
        }
        return  s;
    }

    @RequestMapping("/test")
    public String test()throws Exception{
        List<String> allBucket = minioAdapter.getAllBucket();
        return  allBucket.get(0);
    }

    /**
     * 上传文件
     */
    @RequestMapping("/upload")
    public Result upload(MultipartFile uploadFile, String bucket, String objectName) throws Exception {
        String url = fileService.uploadFile(uploadFile, bucket, objectName);
        System.out.println("上传图片url为:"+url);
        return Result.ok(url);
    }
}
