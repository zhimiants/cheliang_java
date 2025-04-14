package com.atguigu.spzx.h5.product.controller;

import com.atguigu.spzx.h5.product.service.BrandService;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.vo.common.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "品牌管理")
@RestController
@RequestMapping("/api/product/brand")
public class BrandController {

    @Autowired
    BrandService brandService;

    @RequestMapping("findAll")
    public Result getBrandList(){
        List<Brand> list = brandService.findAll();
        return Result.ok(list);
    }
}
