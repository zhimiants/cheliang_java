package com.atguigu.spzx.common.log.annotation;

import com.atguigu.spzx.common.log.aspect.LogAspect;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Date:2024/4/13
 * Author:陈浩微
 * Description:
 *
 *
 * 因为启动类不在这个包里面 扫描不到
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(value = LogAspect.class)
public @interface EnableLogAspect {
}
