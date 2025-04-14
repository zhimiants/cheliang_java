package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.common.log.annotation.Log;
import com.atguigu.spzx.common.log.enums.OperatorType;
import com.atguigu.spzx.manager.service.BrandService;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.vo.common.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "品牌管理")
@RestController
@RequestMapping("/admin/system/brand")
public class BrandController {

    @Autowired
    BrandService brandService;
    @Log(title = "车辆品牌管理：分页查询",businessType = 0,operatorType = OperatorType.MANAGE)
    @Operation(summary = "分页查询品牌列表")
    @GetMapping("findByPage/{pageNum}/{pageSize}")
    public Result findByPage(@PathVariable Integer pageNum,@PathVariable Integer pageSize){
        PageInfo<Brand> pageInfo = brandService.findByPage(pageNum,pageSize);
        return Result.ok(pageInfo);
    }
    @Log(title = "品牌管理：添加",businessType = 1)
    @Operation(summary = "添加品牌")
    @PostMapping("/addBrand")
    public Result addBrand(@RequestBody Brand brand){
        brandService.addBrand(brand);
        return Result.ok(null);
    }

    @Operation(summary = "修改品牌")
    @PostMapping("/updateBrand")
    public Result updateBrand(@RequestBody Brand brand){
        brandService.updateBrand(brand);
        return Result.ok(null);
    }
    @Operation(summary = "删除品牌")
    @DeleteMapping ("/deleteBrand/{id}")
    public Result deleteBrand(@PathVariable Integer id){
        brandService.deleteBrand(id);
        return Result.ok(null);
    }

    @Operation(summary = "查询所有的品牌（商品管理的前端下拉框）")
    @GetMapping("findAll")
    public Result findAll(){
        List<Brand> list = brandService.findAll();
        return Result.ok(list);
    }

}
