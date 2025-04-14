package com.atguigu.spzx.h5.cart.controller;

import com.atguigu.spzx.h5.cart.server.CartService;
import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.vo.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "购物车接口")
@RestController
@RequestMapping("api/order/cart/auth/")
public class CarController {

    @Autowired
    CartService cartService;


    @Operation(summary = "添加购物车(skuid+数量)")
    @GetMapping("addToCart/{skuId}/{skuNum}")
    public Result addToCart(@PathVariable Long skuId,@PathVariable Integer skuNum){
        cartService.addToCart(skuId,skuNum);
        return Result.ok(null);
    }

    @Operation(summary = "查询购物车")
    @GetMapping("cartList")
    public Result cartList(){
        List<CartInfo> cartInfoList = cartService.cartList();
        return Result.ok(cartInfoList);
    }


    @Operation(summary = "修改选中商品状态 ")
    @GetMapping("checkCart/{skuId}/{isChecked}")
    public Result checkCart(@PathVariable Long skuId,@PathVariable Integer isChecked){
        cartService.checkCart(skuId,isChecked);
        return Result.ok(null);
    }

}
