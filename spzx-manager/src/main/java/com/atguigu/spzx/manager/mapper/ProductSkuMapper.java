package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ProductSkuMapper {
    List<ProductSku> findByProductId(Long id);

    void add(ProductSku productSku);

    List<ProductSku> getByProductId(Long id);

    void updateById(ProductSku productSku);

    void deleteByProductId(Long productId);
}
