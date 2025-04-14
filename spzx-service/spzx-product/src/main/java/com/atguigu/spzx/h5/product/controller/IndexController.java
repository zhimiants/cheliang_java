package com.atguigu.spzx.h5.product.controller;

import com.atguigu.spzx.h5.product.service.IndexService;
import com.atguigu.spzx.model.vo.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@Tag(name = "首页管理")
@RestController
@RequestMapping("/api/product/index")
public class IndexController {

    @Autowired
    IndexService indexService;
    @Operation(summary = "获取一级分类列表和Sku列表")
    @GetMapping
    public Result index(){
        Map map = indexService.getMap();
        return Result.ok(map);
    }
}
