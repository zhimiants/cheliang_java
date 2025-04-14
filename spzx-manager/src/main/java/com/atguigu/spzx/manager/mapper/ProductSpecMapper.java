package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.ProductSpec;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ProductSpecMapper {
    List<ProductSpec> selectList();

    void add(ProductSpec productSpec);

    void updateById(ProductSpec productSpec);

    void deleteById(Integer id);

    List<ProductSpec> findAll();

}
