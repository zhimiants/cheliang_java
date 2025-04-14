package com.atguigu.spzx.h5.cart.server.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.h5.cart.server.CartService;
import com.atguigu.spzx.h5.product.client.ProductFeignClient;
import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Date:2024/1/18
 * Author:陈浩微
 * Description:
 * 添加购物车 不需要操作mysql数据库了
 * 而是操作redis数据库,通过ThreadLocal
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Autowired
    ProductFeignClient productFeignClient;

    @Override
    public void addToCart(Long skuId, Integer skuNum) {
        //购物车的大key user:cart:userId 用户id
        String key = "user:cart:" + ThreadLocalUtil.getH5().getId();

        //根据大key和小key(skuId)查找redis中的value cartinfo 购物车信息对象
        Object cartInfo = redisTemplate.opsForHash().get(key,String.valueOf(skuId));
        //判断cartInfo是否为空
        if (cartInfo == null){
            //购物车没有该sku商品 就操作mysql数据库但是通过远程调用的方式
            ProductSku productSku = productFeignClient.getBySkuId(skuId);

            //将数据封装成cartinfo最后转成json字符串
            CartInfo cart = new CartInfo();
            cart.setUserId(ThreadLocalUtil.getH5().getId());//ThreadLocalUtil存储用户信息
            cart.setIsChecked(1); //加入购物车默认勾选
            cart.setSkuId(skuId); //方法的参数
            cart.setSkuName(productSku.getSkuName());
            cart.setCartPrice(productSku.getSalePrice());
            cart.setImgUrl(productSku.getThumbImg());
            cart.setSkuNum(skuNum); //方法的参数

            //小key value -> 要json字符串
            redisTemplate.opsForHash().put(key,String.valueOf(skuId), JSON.toJSONString(cart));
        }else {
            //说明购物车有该sku商品
            //从redis取出来(字符串格式) 转成对象 在转成字符串存进去 加数量就行
            CartInfo cart = JSON.parseObject(String.valueOf(cartInfo), CartInfo.class);
            cart.setSkuNum(cart.getSkuNum() + skuNum);
            //将CartInfo类型的cart 转成json字符串传入redis中
            redisTemplate.opsForHash().put(key,String.valueOf(skuId),JSON.toJSONString(cart));
        }


    }

    @Override
    public List<CartInfo> cartList() {
        //根据用户id(ThreadLocal)生成购物车key去redis中找cartInfo数据
        String key = "user:cart:" + ThreadLocalUtil.getH5().getId();
        //传入大key,返回hash的value集合
        List<Object> objectList = redisTemplate.opsForHash().values(key);

        List<CartInfo> cartInfoList = new ArrayList<>();
        //泛型转换
        objectList.forEach(object -> {
            //object 就是redis中的json字符串
            //将object对象 转成 cartInfo对象
            CartInfo cartInfo = JSON.parseObject(String.valueOf(object), CartInfo.class);
            cartInfoList.add(cartInfo);
        });

        return cartInfoList;
    }

    @Override
    public void checkCart(Long skuId, Integer isChecked) {
        //先根据大key 和 小key(skuId)查数据
        String key = "user:cart:" + ThreadLocalUtil.getH5().getId();
        //这里可以强转字符串,这样类型转换就不用String.valueOf
        Object jsonobject = redisTemplate.opsForHash().get(key, String.valueOf(skuId));

        CartInfo cartInfo = JSON.parseObject(String.valueOf(jsonobject), CartInfo.class);
        cartInfo.setIsChecked(isChecked);

        //再把cartinfo转成json字符串 传入redis
        String toJSONString = JSON.toJSONString(cartInfo);
        redisTemplate.opsForHash().put(key,String.valueOf(skuId),toJSONString);

    }


}
