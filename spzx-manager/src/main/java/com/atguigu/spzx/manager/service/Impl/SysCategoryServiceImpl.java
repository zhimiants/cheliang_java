package com.atguigu.spzx.manager.service.Impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.EasyExcel;
import com.atguigu.spzx.manager.mapper.SysCategoryMapper;
import com.atguigu.spzx.manager.service.SysCategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import jakarta.servlet.http.HttpServletResponse;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


@Service
public class SysCategoryServiceImpl implements SysCategoryService {


    @Autowired
    SysCategoryMapper sysCategoryMapper;

    @Autowired
    CategoryExcelReadListener categoryExcelReadListener;

    /**
     * 根据parentId查询商品的下级分类列表
     * @param parentId
     * @return
     */
    @Override
    public List<Category> findListByParentId(Long parentId) {
        List<Category> list = sysCategoryMapper.findListByParentId(parentId);
        if (CollectionUtil.isNotEmpty(list)){
            //因为数据库查询的结果没有hasChildren属性和children集合
            list.forEach(category -> {
                Long id = category.getId();
                Integer count = sysCategoryMapper.getCountByParentId(id);
                //设置hasChildren属性
                category.setHasChildren(count>0);

            });
        }

        return list;
    }

    @Override
    public void download(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("商品分类数据", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            List<Category> categoryList = sysCategoryMapper.findAll();
            //把Category 转成 把CategoryExcelVo
            List<CategoryExcelVo> categoryExcelVoList = new ArrayList<>();
            categoryList.forEach(category -> {
                CategoryExcelVo categoryExcelVo = new CategoryExcelVo();
                BeanUtils.copyProperties(category,categoryExcelVo);
                categoryExcelVoList.add(categoryExcelVo);
            });
            EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class).
                    sheet("全部商品分类sheet").doWrite(categoryExcelVoList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void importFile(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, CategoryExcelVo.class,categoryExcelReadListener)
                    //导入文件的sheet
                    .sheet("测试数据")
                    .doRead();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
