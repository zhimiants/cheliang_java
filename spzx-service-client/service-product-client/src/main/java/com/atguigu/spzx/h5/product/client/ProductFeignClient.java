package com.atguigu.spzx.h5.product.client;

import com.atguigu.spzx.model.entity.product.ProductSku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(value = "service-product")
public interface ProductFeignClient {
    @GetMapping("/api/product/getBySkuId/{skuId}")
    public ProductSku getBySkuId(@PathVariable Long skuId);
}
