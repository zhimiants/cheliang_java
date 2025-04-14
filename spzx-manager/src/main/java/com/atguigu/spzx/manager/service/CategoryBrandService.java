package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface CategoryBrandService {
    PageInfo findByPage(Integer pageNum, Integer pageSize, CategoryBrandDto categoryBrandDto);

    void addBrandCategory(CategoryBrandDto categoryBrandDto);

    void deleteById(Long id);

    List<Long> getIdList(Long threeId);

    void updateCategoryBrand(CategoryBrand categoryBrand);

    /**
     * 根据分类id查询对应品牌数据
     * @param categoryId
     * @return
     */
    List<Brand> findBrandByCategoryId(Long categoryId);
}
