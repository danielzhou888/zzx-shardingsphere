package com.zzx.sharding.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhouzhixiang
 * @company 叮当快药科技集团有限公司
 * @Date 2020-03-15
 */
@ConfigurationProperties(prefix = "spring.shardingsphere.datasource.slave1")
@Data
public class Slave1Prop {
    private String url;
    private String username;
    private String password;
    private String type;
    private String driverClassName;
}
