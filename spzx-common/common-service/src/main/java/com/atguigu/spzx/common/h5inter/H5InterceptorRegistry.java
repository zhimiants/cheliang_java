package com.atguigu.spzx.common.h5inter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//@Component   自定义注解已经import了
public class H5InterceptorRegistry implements WebMvcConfigurer {

    //把拦截器自动装配进来
    @Autowired
    UserInterceptor userInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor)
                //  .excludePathPatterns() 要排除的接口
                .addPathPatterns("/api/**");//拦截器要拦截的接口


    }
}
