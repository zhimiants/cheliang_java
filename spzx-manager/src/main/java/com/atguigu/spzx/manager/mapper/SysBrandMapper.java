package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface SysBrandMapper {
    /**
     * 分页查询品牌列表
     * @return
     */
    List<Brand> findByPage();

    /**
     * 添加品牌
     * @param brand
     */
    void addBrand(Brand brand);

    Brand selectById(Long id);

    Integer selectCountByName(String name);

    Integer selectCountByLogo(String logo);

    void updateBrand(Brand brand);

    void deleteBrand(Integer id);

    /**
     * 查询所有的品牌
     * @return
     */
    List<Brand> findAll();

}
