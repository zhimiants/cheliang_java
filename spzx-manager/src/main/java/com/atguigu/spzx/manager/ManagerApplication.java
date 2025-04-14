package com.atguigu.spzx.manager;

import com.atguigu.spzx.common.log.annotation.EnableLogAspect;
import com.atguigu.spzx.manager.auth.NoAuthPathList;
import com.atguigu.spzx.manager.properties.MinioProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Date:2023/12/27
 * Author:陈浩微
 * Description:
 * 启动类
 * @EnableScheduling 启动定时任务的注解
 *
 *
 *
 */
@EnableScheduling
@EnableLogAspect
@EnableConfigurationProperties(value = {NoAuthPathList.class, MinioProperties.class})
@ComponentScan(basePackages = "com.atguigu")//为了扫描到common-service下的Knife4jConfig配置类
@SpringBootApplication
@EnableAsync
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class,args);
    }
}
