package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.ProductSpecService;
import com.atguigu.spzx.model.entity.product.ProductSpec;
import com.atguigu.spzx.model.vo.common.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "商品规格")
@RestController
@RequestMapping(value = "/admin/system/productSpec")
public class ProductSpecController {

    @Autowired
    ProductSpecService productSpecService;

    @Operation(summary = "规格分页查询")
    @GetMapping("/findByPage/{pageNum}/{pageSize}")
    public Result findByPage(@PathVariable Integer pageNum,@PathVariable Integer pageSize){
        PageInfo<ProductSpec> pageInfo = productSpecService.findByPage(pageNum,pageSize);
        return Result.ok(pageInfo);

    }

    @Operation(summary = "添加规格")
    @PostMapping("add")
    public Result add(@RequestBody ProductSpec productSpec){
        productSpecService.add(productSpec);
        return Result.ok(null);
    }

    @Operation(summary = "修改规格")
    @PutMapping("update")
    public Result updateById(@RequestBody ProductSpec productSpec){
        productSpecService.updateById(productSpec);
        return Result.ok(null);
    }
    @Operation(summary = "删除规格")
    @DeleteMapping("deleteById/{id}")
    public Result deleteById(@PathVariable Integer id){
        productSpecService.deleteById(id);
        return Result.ok(null);
    }

    @Operation(summary = "查询所有规格")
    @GetMapping("findAll")
    public Result findAll() {
        List<ProductSpec> list = productSpecService.findAll();
        return Result.ok(list) ;
    }
}
