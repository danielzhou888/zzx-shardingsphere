package com.zzx.sharding.config;


import com.zzx.sharding.mapper.TeamMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Panlf
 * @date 2019/6/12
 */
@Configuration
@Slf4j
@MapperScan(basePackages = "com.zzx.sharding.mapper", sqlSessionFactoryRef = "sessionFactory")
public class DataSourceConfig {

    //@Bean
    //@ConfigurationProperties(prefix = "mybatis.configuration")
    //public org.apache.ibatis.session.Configuration configuration(){
    //    org.apache.ibatis.session.Configuration configuration =  new org.apache.ibatis.session.Configuration();
    //    configuration.setMapUnderscoreToCamelCase(true);
    //    configuration.getTypeAliasRegistry().registerAliases("com.zzx.sharding.entity");
    //    //configuration.addMappers("classpath*:mapper/*.xml");
    //    //configuration.getMapperRegistry().addMappers("classpath*:mapper/*.xml");
    //    configuration.addMapper(TeamMessageMapper.class);
    //    return configuration;
    //}

    /**
     * 配置数据源0，数据源的名称最好要有一定的规则，方便配置分库的计算规则
     *
     * @return
     */
    @Bean(name = "master")
    @ConfigurationProperties(prefix = "spring.shardingsphere.datasource.master")
    public DataSource master() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 配置数据源1，数据源的名称最好要有一定的规则，方便配置分库的计算规则
     *
     * @return
     */
    @Bean(name = "slave1")
    @ConfigurationProperties(prefix = "spring.shardingsphere.datasource.slave1")
    public DataSource dataSource1() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 配置数据源1，数据源的名称最好要有一定的规则，方便配置分库的计算规则
     *
     * @return
     */
    @Bean(name = "slave0")
    @ConfigurationProperties(prefix = "spring.shardingsphere.datasource.slave0")
    public DataSource dataSource0() {
        return DataSourceBuilder.create().build();
    }


    @Bean
    public SqlSessionFactory sessionFactory(DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setTypeAliasesPackage("com.zzx.sharding.entity");

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        sessionFactory.setConfiguration(configuration);

        return sessionFactory.getObject();
    }

    @Bean
    public DataSource dataSource() throws SQLException {

        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();

        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("master", dataSource0());
        dataSourceMap.put("slave0", dataSource1());
        dataSourceMap.put("slave1", dataSource1());

        List<String> slaveList = new ArrayList<>();
        slaveList.add("slave0");
        slaveList.add("slave1");
        MasterSlaveRuleConfiguration masterSlaveRuleConfiguration = new MasterSlaveRuleConfiguration("ms", "master", slaveList);
        masterSlaveRuleConfiguration.getLoadBalanceStrategyConfiguration().getProperties().setProperty("type", "round_robin");
        //TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration("t_order", "ds${0..1}.t_order_${0..1}");
        // 配置分库 + 分表策略
        //orderTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds${user_id % 2}"));
        //orderTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "t_order_${order_id % 2}"));


        //shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);

        shardingRuleConfig.getMasterSlaveRuleConfigs().add(masterSlaveRuleConfiguration);

        // 获取数据源对象
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new Properties());
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager shardTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
