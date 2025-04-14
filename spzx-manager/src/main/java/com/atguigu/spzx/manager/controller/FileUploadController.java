package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.FileUploadService;
import com.atguigu.spzx.model.vo.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/admin/system/file")
public class FileUploadController {

    @Autowired
    FileUploadService fileUploadService;

    //使用MultipartFile参数类型,接收前端的文件
    @Operation(summary = "上传头像文件")
    @PostMapping("/uploadFile")
    public Result uploadFile(MultipartFile file){
        String url = fileUploadService.uploadFile(file);
        return Result.ok(url);
    }
}
