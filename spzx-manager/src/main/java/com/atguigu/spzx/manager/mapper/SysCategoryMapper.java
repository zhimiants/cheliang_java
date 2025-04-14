package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


@Mapper
public interface SysCategoryMapper {
    /**
     * 根据parentId查询商品的下级分类列表
     * @param parentId
     * @return
     */
    List<Category> findListByParentId(Long parentId);

    /**
     * 查询是否有下级分类  hasChildren
     * @param id
     * @return
     */
    Integer getCountByParentId(Long id);

    List<Category> findAll();


    void batchAddCategory(List<Category> batchList);

    Map<String, Long> getIdListByTheeId(Long threeId);
}
