package com.zzx.sharding.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;
import java.util.Map;

public class DataSourceUtil {


    private static final Logger logger = LoggerFactory.getLogger(DataSourceUtil.class);



    private static final String DATASOURCE_TYPE_DEFAULT = "com.zaxxer.hikari.HikariDataSource";

    public static DataSource buildDataSource(Map<String, Object> dataSourceMap) {
        Object type = dataSourceMap.get("type");
        if (type == null) {
            type = DATASOURCE_TYPE_DEFAULT;
        }
        try {
            Class<? extends DataSource> dataSourceType;
            dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);
            String driverClassName = dataSourceMap.get("driverClassName").toString();
            logger.info("driverClassName="+driverClassName);

            String url = dataSourceMap.get("url").toString();
            String username = dataSourceMap.get("username").toString();
            String password = dataSourceMap.get("password").toString();
            // 自定义DataSource配置
            DataSourceBuilder factory = DataSourceBuilder.create().url(url).username(username).password(password).type(dataSourceType).driverClassName(driverClassName);
            return factory.build();
        } catch (Exception e) {
            logger.error("构建数据源" + type + "出错", e);
        }
        return null;
    }

}
