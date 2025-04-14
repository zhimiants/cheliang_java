package com.atguigu.spzx.h5.product.mapper;

import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductDetails;
import com.atguigu.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ProductMapper {
    List<ProductSku> findByPage(ProductSkuDto productSkuDto);

    ProductSku getProductSkuBySkuId(Long skuId);

    Product getProductByProductId(Long productId);

    ProductDetails getProductDetailsByProductId(Long id);

    List<ProductSku> getProductSkuListByProductId(Long id);
}
