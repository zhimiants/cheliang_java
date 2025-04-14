package com.atguigu.spzx.manager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties(prefix = "spzx.minio")
public class MinioProperties {
    private String endpoint;
    private String user;
    private String password;
    private String bucket;
}
