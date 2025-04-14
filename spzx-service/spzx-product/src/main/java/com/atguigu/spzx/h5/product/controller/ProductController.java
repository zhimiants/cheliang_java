package com.atguigu.spzx.h5.product.controller;

import com.atguigu.spzx.h5.product.service.ProductService;
import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.h5.ProductItemVo;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;


    @Operation(summary = "商品分页查询")
    @GetMapping("/{page}/{limit}")
    public Result findByPage(@PathVariable Long page, @PathVariable Long limit, ProductSkuDto productSkuDto){
        PageInfo pageInfo = productService.findByPage(page,limit,productSkuDto);
        return Result.ok(pageInfo);
    }

    @Operation(summary = "商品详情查询")
    @GetMapping("/item/{skuId}")
    public Result item(@PathVariable Long skuId){
        ProductItemVo productItemVo = productService.item(skuId);
        return Result.ok(productItemVo);
    }

    //微服务的远程调用,根据skuid查询sku对象,给cart服务使用
    @Operation(summary = "根据商品skuId查询sku对象")
    @GetMapping("getBySkuId/{skuId}")
    public ProductSku getBySkuId(@PathVariable Long skuId){
        ProductSku productSku = productService.getBySkuId(skuId);
        return productSku;
    }


}
