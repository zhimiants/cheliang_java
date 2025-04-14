package com.atguigu.spzx.h5.product.service.Impl;

import com.atguigu.spzx.h5.product.mapper.IndexMapper;
import com.atguigu.spzx.h5.product.service.IndexService;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.entity.product.ProductSku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    IndexMapper indexMapper;

    @Override
    public Map getMap() {

        List<ProductSku> productSkuList = indexMapper.getProductSkuList();

        List<Category> categoryList = indexMapper.getCategoryList();

        Map map = new HashMap();
        map.put("productSkuList",productSkuList);
        map.put("categoryList",categoryList);

        return map;
    }
}
