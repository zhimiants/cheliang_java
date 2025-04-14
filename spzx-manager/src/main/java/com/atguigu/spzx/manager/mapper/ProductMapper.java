package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ProductMapper {
    //查询品牌名 和 3个分类名
    List<Product> selectList(ProductDto productDto);

    void add(Product product);

    Product getById(Long id);

    void updateById(Product product);

    void deleteById(Long id);
}
