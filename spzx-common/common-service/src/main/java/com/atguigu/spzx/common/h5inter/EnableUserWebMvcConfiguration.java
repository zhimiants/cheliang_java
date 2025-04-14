package com.atguigu.spzx.common.h5inter;


import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Import(value = { UserInterceptor.class , H5InterceptorRegistry.class})
public @interface EnableUserWebMvcConfiguration {
}