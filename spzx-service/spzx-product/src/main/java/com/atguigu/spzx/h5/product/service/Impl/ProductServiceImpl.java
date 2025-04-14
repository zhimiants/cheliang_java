package com.atguigu.spzx.h5.product.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.atguigu.spzx.h5.product.mapper.ProductMapper;
import com.atguigu.spzx.h5.product.service.ProductService;
import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductDetails;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.h5.ProductItemVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;
    @Override
    public PageInfo findByPage(Long page, Long limit, ProductSkuDto productSkuDto) {
        PageHelper.startPage(Integer.parseInt(page + ""),Integer.parseInt(limit + ""));


        List<ProductSku> list = productMapper.findByPage(productSkuDto);


        return new PageInfo(list);
    }

    @Override
    public ProductItemVo item(Long skuId) {
        ProductItemVo productItemVo = new ProductItemVo();

        //1.根据skuid查询sku对象
        ProductSku productSku = productMapper.getProductSkuBySkuId(skuId);
        //2.查询spu对象
        Product product = productMapper.getProductByProductId(productSku.getProductId());
        //3.查询轮播图集合 "url1,url2"  要把字符串变成集合
        //轮播图字符串
        String sliderUrls = product.getSliderUrls();
        //字符串 -> 数组
        String[] split = sliderUrls.split(",");
        //数组 -> 集合
        List<String> sliderUrlList = Arrays.asList(split);

        //4.查询详情图
        ProductDetails productDetails = productMapper.getProductDetailsByProductId(product.getId());
        String imageUrls = productDetails.getImageUrls();
        String[] split1 = imageUrls.split(",");
        List<String> detailsImageUrlList = Arrays.asList(split1);


        //5.skuSpecValueMap  key:sku的sku_spec value 是skuid
        Map<String,Object> skuSpecValueMap = new HashMap<>();
        List<ProductSku> skuList = productMapper.getProductSkuListByProductId(product.getId());
        for (ProductSku sku : skuList) {
            String key = sku.getSkuSpec();
            Long value = sku.getId();
            skuSpecValueMap.put(key,value);
        }

        //6.specValueList  把json字符串 变成json数组
        //获取json字符串
        String specValue = product.getSpecValue();
        //转成json数组
        JSONArray jsonArray = JSON.parseArray(specValue);

        productItemVo.setProductSku(productSku);
        productItemVo.setProduct(product);
        productItemVo.setSliderUrlList(sliderUrlList);
        productItemVo.setDetailsImageUrlList(detailsImageUrlList);
        productItemVo.setSkuSpecValueMap(skuSpecValueMap);
        productItemVo.setSpecValueList(jsonArray);

        return productItemVo;
    }

    @Override
    public ProductSku getBySkuId(Long skuId) {
        return productMapper.getProductSkuBySkuId(skuId);
    }
}
