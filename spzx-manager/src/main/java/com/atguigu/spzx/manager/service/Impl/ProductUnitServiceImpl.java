package com.atguigu.spzx.manager.service.Impl;

import com.atguigu.spzx.manager.mapper.ProductUnitMapper;
import com.atguigu.spzx.manager.service.ProductService;
import com.atguigu.spzx.manager.service.ProductUnitService;
import com.atguigu.spzx.model.entity.base.ProductUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductUnitServiceImpl implements ProductUnitService {

    @Autowired
    ProductUnitMapper productUnitMapper;

    @Override
    public List<ProductUnit> findAll() {
        return productUnitMapper.findAll();
    }
}
