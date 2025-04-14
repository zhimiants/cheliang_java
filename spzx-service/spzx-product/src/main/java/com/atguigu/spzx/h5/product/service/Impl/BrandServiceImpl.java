package com.atguigu.spzx.h5.product.service.Impl;

import com.atguigu.spzx.h5.product.mapper.BrandMapper;
import com.atguigu.spzx.h5.product.service.BrandService;
import com.atguigu.spzx.model.entity.product.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandMapper brandMapper;
    @Override
    public List<Brand> findAll() {
        return brandMapper.findAll();
    }
}
