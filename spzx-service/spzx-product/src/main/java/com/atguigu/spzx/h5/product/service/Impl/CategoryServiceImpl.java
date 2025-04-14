package com.atguigu.spzx.h5.product.service.Impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.h5.product.mapper.CategoryMapper;
import com.atguigu.spzx.h5.product.service.CategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    CategoryMapper categoryMapper;

    //编程式缓存  格式json字符串
    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @Override
    public List<Category> findCategoryTree() {


        //实现编程式缓存
//        String jsonString = redisTemplate.opsForValue().get("category:one");
//        if(!StringUtils.isEmpty(jsonString)){
//            List<Category> categoryList = JSON.parseArray(jsonString, Category.class);
//            return categoryList;
//        }


        //要求只执行一次sql

        //获取所有的分类信息
        List<Category> allCategoryList = categoryMapper.findAll();
        //过滤得到所有的一级分类
        List<Category> oneList = allCategoryList.stream().filter(category ->
                category.getParentId() == 0
        ).collect(Collectors.toList());

        oneList.forEach(one -> {
            //一级分类id
            Long oneId = one.getId();

            List<Category> twoList = allCategoryList.stream().filter(category -> category.getParentId() == oneId)
                    .collect(Collectors.toList());

            //遍历二级分类集合,设置二级分类的children
            twoList.forEach(two -> {
                Long twoId = two.getId();
                List<Category> threeList = allCategoryList.stream().filter(category -> category.getParentId() == twoId)
                        .collect(Collectors.toList());
                two.setChildren(threeList);
            });
            one.setChildren(twoList);

        });
//        String toJSONString = JSON.toJSONString(oneList);
//        redisTemplate.opsForValue().set("category:one",toJSONString,5, TimeUnit.MINUTES);


        //返回一级分类
        return oneList;
    }
}
