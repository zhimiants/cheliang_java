package com.atguigu.spzx.h5.product.service;

import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.h5.ProductItemVo;
import com.github.pagehelper.PageInfo;


public interface ProductService {
    PageInfo findByPage(Long page, Long limit, ProductSkuDto productSkuDto);

    ProductItemVo item(Long skuId);

    ProductSku getBySkuId(Long skuId);

}
