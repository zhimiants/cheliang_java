package com.atguigu.spzx.h5.product.mapper;

import com.atguigu.spzx.model.entity.product.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface BrandMapper {
    List<Brand> findAll();

}
