package com.atguigu.spzx.h5.user;


import com.atguigu.spzx.common.h5inter.EnableUserWebMvcConfiguration;
import com.atguigu.spzx.h5.user.utils.MessageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;


@EnableConfigurationProperties(value = MessageProperties.class)
@ComponentScan( basePackages = {
        "com.atguigu.spzx.common.exp",
        "com.atguigu.spzx.common.knife4j",
        "com.atguigu.spzx.h5.user",
        "com.atguigu.spzx.common.h5inter"
})
@EnableUserWebMvcConfiguration
@SpringBootApplication
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}
