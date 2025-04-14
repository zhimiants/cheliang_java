package com.atguigu.spzx.manager.service.Impl;

import com.atguigu.spzx.common.exp.GuiguException;
import com.atguigu.spzx.manager.mapper.CategoryBrandMapper;
import com.atguigu.spzx.manager.mapper.SysCategoryMapper;
import com.atguigu.spzx.manager.service.CategoryBrandService;
import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Service
public class CategoryBrandServiceImpl implements CategoryBrandService {

    @Autowired
    CategoryBrandMapper categoryBrandMapper;

    @Autowired
    SysCategoryMapper sysCategoryMapper;

    @Override
    public PageInfo findByPage(Integer pageNum, Integer pageSize, CategoryBrandDto categoryBrandDto) {
        //设置分页参数
        PageHelper.startPage(pageNum,pageSize);
        List<CategoryBrand> list = categoryBrandMapper.selectList(categoryBrandDto);
        return new PageInfo(list);
    }

    @Override
    public void addBrandCategory(CategoryBrandDto categoryBrandDto) {
        Integer count = categoryBrandMapper.selectCount(categoryBrandDto);
        if (count > 0){
            throw new GuiguException(222,"该品牌分类数据已存在,请勿重复创建");
        }
        categoryBrandMapper.addBrandCategory(categoryBrandDto);
    }

    @Override
    public void deleteById(Long id) {
        categoryBrandMapper.deleteById(id);
    }

    @Override
    public List<Long> getIdList(Long threeId) {
        Map<String,Long> map = sysCategoryMapper.getIdListByTheeId(threeId);
        List<Long> idList = Arrays.asList(map.get("one"), map.get("two"), map.get("three"));
        return idList;
    }

    @Override
    public void updateCategoryBrand(CategoryBrand categoryBrand) {
        //非空校验 前端页面的数据
        Long id = categoryBrand.getId();
        Long brandId = categoryBrand.getBrandId();
        Long categoryId = categoryBrand.getCategoryId();

        //根据id关联数据查询
        CategoryBrand categoryBrandFromBD = categoryBrandMapper.getById(id);
        //判断是否修改
        if (categoryBrandFromBD.getBrandId().equals(brandId) && categoryBrandFromBD.getCategoryId().equals(categoryId)){
            throw new GuiguException(266,"没有修改任何数据");
        }
        //判断数据库中是否重复  前端修改时传来的
        CategoryBrandDto categoryBrandDto = new CategoryBrandDto();
        categoryBrandDto.setBrandId(brandId);
        categoryBrandDto.setCategoryId(categoryId);
        //已经有了
        Integer count = categoryBrandMapper.selectCount(categoryBrandDto);
        if(count > 0){
            throw new GuiguException(266,"该关联数据已存在,请勿重复创建");
        }

        //真正的修改
        categoryBrandMapper.updateCategoryBrand(categoryBrand);


    }

    /**
     * 根据分类id查询对应品牌数据
     * @param categoryId
     * @return
     */
    @Override
    public List<Brand> findBrandByCategoryId(Long categoryId) {
        //根据分类id查询对应品牌数据
        List<Brand> list = categoryBrandMapper.findBrandByCategoryId(categoryId);

        return list;
    }
}
