package com.atguigu.spzx.manager.controller;


import com.atguigu.spzx.manager.service.SysCategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.List;


@Tag(name = "商品分类")
@RestController
@RequestMapping("/admin/system/category")
public class CategoryController {

    @Autowired
    SysCategoryService sysCategoryService;

    @Operation(summary = "根据parentId查询商品的下级分类列表")
    @GetMapping("/findListByParentId/{parentId}")
    public Result findListByParentId(@PathVariable Long parentId){
        List<Category> list = sysCategoryService.findListByParentId(parentId);
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "文件下载")
    @GetMapping("/download")
    public void download(HttpServletResponse response) throws IOException {
        sysCategoryService.download(response);
    }

    @Operation(summary = "文件导入数据库")
    @PostMapping("/importFile")
    public Result importFile(MultipartFile file){
        sysCategoryService.importFile(file);
        return Result.ok(null);
    }
}
