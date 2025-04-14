package com.atguigu.spzx.h5.user.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "aliyun.message")
public class MessageProperties {
    private String host;
    private String path;
    private String appcode;
    private String method;
}