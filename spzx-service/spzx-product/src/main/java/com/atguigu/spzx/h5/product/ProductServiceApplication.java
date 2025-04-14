package com.atguigu.spzx.h5.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(basePackages = {
        "com.atguigu.spzx.common.exp",
        "com.atguigu.spzx.common.knife4j",
        "com.atguigu.spzx.h5.product",
        // "com.atguigu.spzx.common.cors"

})
@SpringBootApplication
public class ProductServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class,args);
    }
}
