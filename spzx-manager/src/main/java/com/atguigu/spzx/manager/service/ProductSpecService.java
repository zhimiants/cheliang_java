package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.product.ProductSpec;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface ProductSpecService {
    PageInfo findByPage(Integer pageNum, Integer pageSize);

    void add(ProductSpec productSpec);

    void updateById(ProductSpec productSpec);

    void deleteById(Integer id);

    List<ProductSpec> findAll();

}
