package com.atguigu.spzx.manager.service.Impl;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.atguigu.spzx.manager.mapper.SysCategoryMapper;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class CategoryExcelReadListener extends AnalysisEventListener<CategoryExcelVo> {

    //读取数据最多10条 执行一次sql
    private List<Category> batchList = ListUtils.newArrayListWithExpectedSize(10);
    @Autowired
    SysCategoryMapper sysCategoryMapper;
    @Override
    public void invoke(CategoryExcelVo categoryExcelVo, AnalysisContext context) {
        Category category = new Category();
        //VO是文档的数据
        BeanUtils.copyProperties(categoryExcelVo,category);
        //sql语句和Category类对应
        batchList.add(category);

        if (batchList.size() == 10){
            sysCategoryMapper.batchAddCategory(batchList);
            //原来的集合会自动GC回收,节约空间
            batchList = ListUtils.newArrayListWithExpectedSize(10);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        //导入剩余的数据
        if (batchList.size()>0){
            sysCategoryMapper.batchAddCategory(batchList);
            //注意,没有这句集合中有残留的数据,就会导入失败
            batchList = ListUtils.newArrayListWithExpectedSize(10);
        }
    }
}
