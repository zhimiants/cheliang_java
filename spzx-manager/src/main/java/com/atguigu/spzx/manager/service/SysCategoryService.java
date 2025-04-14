package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.product.Category;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface SysCategoryService {
    /**
     * 根据parentId查询商品的下级分类列表
     * @param parentId
     * @return
     */
    List<Category> findListByParentId(Long parentId);

    void download(HttpServletResponse response);

    /**
     * Excel文件导入
     * @param file
     */
    void importFile(MultipartFile file);
}
