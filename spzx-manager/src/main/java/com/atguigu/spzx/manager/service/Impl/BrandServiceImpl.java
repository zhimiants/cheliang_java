package com.atguigu.spzx.manager.service.Impl;

import com.atguigu.spzx.common.exp.GuiguException;
import com.atguigu.spzx.manager.mapper.SysBrandMapper;
import com.atguigu.spzx.manager.service.BrandService;
import com.atguigu.spzx.model.entity.product.Brand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    SysBrandMapper sysBrandMapper;

    /**
     * 分页查询品牌列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo findByPage(Integer pageNum, Integer pageSize) {
        //设置分页参数
        PageHelper.startPage(pageNum,pageSize);
        List<Brand> list = sysBrandMapper.findByPage();
        PageInfo<Brand> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 添加品牌
     * @param brand
     */
    @Override
    public void addBrand(Brand brand) {
        sysBrandMapper.addBrand(brand);
    }

    @Override
    public void updateBrand(Brand brand) {
        Long id = brand.getId();
        //1.name 和 logo 的非空校验
        String name = brand.getName();
        String logo = brand.getLogo();
        if(StringUtils.isEmpty(name)){
            throw new GuiguException(266,"品牌名称不能为空");
        }
        if(StringUtils.isEmpty(logo)){
            throw new GuiguException(266,"品牌图标不能为空");
        }
        //2.判断点击修改的当前品牌和修改的是否是同一个品牌name,可以改图标
        //不是同一个品牌,不能改成库中已经存在的数据
        Brand brandByDB = sysBrandMapper.selectById(id);
        if (!brandByDB.getName().equals(name)){
            Integer count = sysBrandMapper.selectCountByName(name);
            if (count>0){
                throw new GuiguException(207,"该品牌已被注册");
            }
        }
        if (!brandByDB.getLogo().equals(logo)){
            Integer count = sysBrandMapper.selectCountByLogo(logo);
            if (count>0){
                throw new GuiguException(207,"该图标已被注册");
            }
        }
        sysBrandMapper.updateBrand(brand);
    }

    @Override
    public void deleteBrand(Integer id) {
        sysBrandMapper.deleteBrand(id);
    }

    /**
     * 查询所有的品牌
     * @return
     */
    @Override
    public List<Brand> findAll() {
        List<Brand> list = sysBrandMapper.findAll();
        return list;
    }
}
