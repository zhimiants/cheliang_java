package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.ProductService;
import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.vo.common.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "商品管理")
@RestController
@RequestMapping("/admin/system/product")
public class ProductController {

    @Autowired
    ProductService productService;


    @Operation(summary = "商品管理分页查询")
    @PostMapping("findByPage/{pageNum}/{pageSize}")
    public Result findByPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize,@RequestBody ProductDto productDto){
        //ProductDto是查询条件
        PageInfo<Product> pageInfo = productService.findByPage(pageNum,pageSize,productDto);
        return Result.ok(pageInfo);
    }

    @Operation(summary = "商品管理添加商品")
    @PostMapping("add")
    public Result add(@RequestBody Product product){
        productService.add(product);
        return Result.ok(null);
    }
    @Operation(summary = "根据id获取商品信息")
    @GetMapping("getById/{id}")
    public Result getById(@PathVariable Long id){
        Product product = productService.getById(id);
        return Result.ok(product);
    }


    @Operation(summary = "商品管理修改商品")
    @PutMapping("updateById")
    public Result update(@RequestBody Product product){
        productService.update(product);
        return Result.ok(null);
    }
    @Operation(summary = "商品管理删除商品")
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id) {
        productService.deleteById(id);
        return Result.ok(null);
    }
    @Operation(summary = "商品管理之审核")
    @GetMapping("/updateAuditStatus/{id}/{auditStatus}")
    public Result updateAuditStatus(@PathVariable Long id,@PathVariable Integer auditStatus){
        productService.updateAuditStatus(id,auditStatus);
        return Result.ok(null);
    }
    @Operation(summary = "商品管理之上下架")
    @GetMapping("/updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        productService.updateStatus(id, status);
        return Result.ok(null);
    }

}
