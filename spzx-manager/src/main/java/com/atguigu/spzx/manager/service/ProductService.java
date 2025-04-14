package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface ProductService {
    /**
     * 商品管理分页查询
     * @param pageNum
     * @param pageSize
     * @param productDto
     * @return
     */
    PageInfo findByPage(Integer pageNum, Integer pageSize, ProductDto productDto);

    void add(Product product);

    Product getById(Long id);

    void update(Product product);

    void deleteById(Long id);

    void updateAuditStatus(Long id, Integer auditStatus);

    void updateStatus(Long id, Integer status);
}
