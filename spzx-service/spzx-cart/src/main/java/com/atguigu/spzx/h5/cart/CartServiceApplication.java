package com.atguigu.spzx.h5.cart;

import com.atguigu.spzx.common.h5inter.EnableUserWebMvcConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


//排除数据库的自动配置
@ComponentScan(basePackages = {
        "com.atguigu.spzx.common.exp",
        "com.atguigu.spzx.common.knife4j",
        "com.atguigu.spzx.h5.cart",
        // "com.atguigu.spzx.common.cors"

})
//使用拦截器注解
@EnableUserWebMvcConfiguration
@EnableFeignClients(basePackages = "com.atguigu.spzx.h5.product.client")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class CartServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartServiceApplication.class,args);
    }

}
