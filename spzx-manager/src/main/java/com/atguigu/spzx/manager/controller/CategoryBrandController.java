package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.CategoryBrandService;
import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import com.atguigu.spzx.model.vo.common.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "分类品牌管理")
@RestController
@RequestMapping("/admin/system/categoryBrand")
public class CategoryBrandController {

    @Autowired
    CategoryBrandService categoryBrandService;

    @Operation(summary = "分页查询分类品牌列表")
    @PostMapping("/findByPage/{pageNum}/{pageSize}")
    public Result findByPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize, @RequestBody CategoryBrandDto categoryBrandDto){
        PageInfo pageInfo = categoryBrandService.findByPage(pageNum,pageSize,categoryBrandDto);
        return Result.ok(pageInfo);
    }
    @Operation(summary = "建立品牌和分类的关联数据")
    @PostMapping("/addBrandCategory")
    public Result addBrandCategory( @RequestBody CategoryBrandDto categoryBrandDto){
        categoryBrandService.addBrandCategory(categoryBrandDto);
        return Result.ok(null);

    }
    @Operation(summary = "删除品牌和分类的关联数据")
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id){
        categoryBrandService.deleteById(id);
        return Result.ok(null);

    }
    @Operation(summary = "根据第三级分类id得到id集合")
    @GetMapping("/getIdList/{threeId}")
    public Result getIdList(@PathVariable Long threeId){
        List<Long> idList = categoryBrandService.getIdList(threeId);
        return Result.ok(idList);
    }

    @Operation(summary = "修改品牌和分类的关联数据")
    @PostMapping("/updateCategoryBrand")
    public Result updateCategoryBrand(@RequestBody CategoryBrand categoryBrand){
        categoryBrandService.updateCategoryBrand(categoryBrand); //修改弹窗 brandName logo
        return Result.ok(null);

    }

    @Operation(summary = "根据分类id查询对应品牌数据")
    @GetMapping("/findBrandByCategoryId/{categoryId}")
    public Result findBrandByCategoryId(@PathVariable Long categoryId){
        List<Brand> list = categoryBrandService.findBrandByCategoryId(categoryId);
        return Result.ok(list);

    }
}
