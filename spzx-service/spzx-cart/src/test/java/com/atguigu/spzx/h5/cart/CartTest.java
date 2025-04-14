package com.atguigu.spzx.h5.cart;

import com.atguigu.spzx.h5.product.client.ProductFeignClient;
import com.atguigu.spzx.model.entity.product.ProductSku;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class CartTest {

    @Autowired
    ProductFeignClient productFeignClient;

    @Test
    public void testProductSkuBySkuId(){
        ProductSku productSku = productFeignClient.getBySkuId(7L);
        System.out.println(productSku);
    }
}
