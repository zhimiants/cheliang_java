package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ProductDetailsMapper {

    ProductDetails findByProductId(Long id);

    void add(ProductDetails productDetails);

    ProductDetails getByProductId(Long id);

    void updateById(ProductDetails productDetails);

    void deleteByProductId(Long productId);
}
