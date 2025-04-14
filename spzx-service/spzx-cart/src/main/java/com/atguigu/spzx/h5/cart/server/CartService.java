package com.atguigu.spzx.h5.cart.server;

import com.atguigu.spzx.model.entity.h5.CartInfo;

import java.util.List;


public interface CartService {
    void addToCart(Long skuId, Integer skuNum);

    List<CartInfo> cartList();

    void checkCart(Long skuId, Integer isChecked);
}
