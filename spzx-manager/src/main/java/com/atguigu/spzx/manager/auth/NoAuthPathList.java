package com.atguigu.spzx.manager.auth;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;


@Data
@ConfigurationProperties(prefix = "spzx.auth")
public class NoAuthPathList {
    //list 要和配置文件前缀后面的名字一样
    private List<String> list;
}
