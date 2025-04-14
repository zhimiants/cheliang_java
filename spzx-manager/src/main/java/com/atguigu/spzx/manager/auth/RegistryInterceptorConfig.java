package com.atguigu.spzx.manager.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class RegistryInterceptorConfig implements WebMvcConfigurer {
    @Autowired
    UserLoginAuthInterceptor userLoginAuthInterceptor;

    @Autowired
    NoAuthPathList noAuthPathList;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginAuthInterceptor)
                .excludePathPatterns(noAuthPathList.getList())  //不拦截的路径
//                .excludePathPatterns(
//                        "/admin/system/index/login",
//                        "/admin/system/index/generateValidateCode")
                .addPathPatterns("/**");  //拦截的路径

    }
}
