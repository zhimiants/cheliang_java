package com.atguigu.spzx.manager.service.Impl;

import cn.hutool.core.date.DateTime;
import com.atguigu.spzx.manager.properties.MinioProperties;
import com.atguigu.spzx.manager.service.FileUploadService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;


@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    MinioProperties minioProperties;
    @Override
    public String uploadFile(MultipartFile file) {
        try {
            //建立minio客户端对象
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(minioProperties.getEndpoint()) //minio的ip和端口号
                            .credentials(minioProperties.getUser(), minioProperties.getPassword())
                            .build();

            //2.判断bucket是否存在
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucket()).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucket()).build());
            } else {
                System.out.println("Bucket 'spzx-sysuser-avatar' already exists.");
            }


            //4.基于文件流的方式，进行文件上传
            InputStream inputStream = file.getInputStream();
            //4.1拼接日期目录字符串
            String dir = new DateTime().toString("yyyy/MM/dd");
            //4.2设置文件名字 uuid + "_" + 源文件名
            //获取源文件名
            String originalFilename = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String fileName = uuid + "_" + originalFilename;
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(minioProperties.getBucket()).object(dir + "/" + fileName).stream(
                                    inputStream, inputStream.available(), -1)
                            .contentType("image/jpeg") //上传的文件的类型
                            .build());
            //5.返回url地址
            String url = minioProperties.getEndpoint() + "/" + minioProperties.getBucket() + "/" + dir + "/" + fileName;
            return url;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
