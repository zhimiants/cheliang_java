package com.atguigu.spzx.h5.product.service;

import com.atguigu.spzx.model.entity.product.Category;

import java.util.List;


public interface CategoryService {
    List<Category> findCategoryTree();

}
