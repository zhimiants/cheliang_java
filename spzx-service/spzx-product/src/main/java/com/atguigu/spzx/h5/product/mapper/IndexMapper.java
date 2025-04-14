package com.atguigu.spzx.h5.product.mapper;

import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface IndexMapper {
    List<ProductSku> getProductSkuList();

    List<Category> getCategoryList();
}
