package com.atguigu.spzx.h5.product.controller;

import com.atguigu.spzx.h5.product.service.CategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "分类管理")
@RestController
@RequestMapping("/api/product/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;


    @Operation(summary = "获取分类树数据")
    @GetMapping("/findCategoryTree")
    public Result findCategoryTree(){
        //获取一级分类列表
        List<Category> list = categoryService.findCategoryTree();
        return Result.ok(list);
    }
}
