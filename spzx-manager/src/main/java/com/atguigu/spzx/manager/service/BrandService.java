package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.product.Brand;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface BrandService {
    /**
     * 分页查询品牌列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo findByPage(Integer pageNum, Integer pageSize);

    /**
     * 添加品牌
     * @param brand
     */
    void addBrand(Brand brand);

    void updateBrand(Brand brand);

    void deleteBrand(Integer id);

    /**
     * 查询所有的品牌
     * @return
     */
    List<Brand> findAll();
}

