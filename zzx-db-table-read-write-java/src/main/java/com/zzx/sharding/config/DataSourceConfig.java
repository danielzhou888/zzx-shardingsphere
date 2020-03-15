package com.zzx.sharding.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.zzx.sharding.utils.DataSourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.masterslave.LoadBalanceStrategyConfiguration;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * @author zhouzhixiang
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@EnableConfigurationProperties({Master0Prop.class, Master1Prop.class, Slave0Prop.class, Slave1Prop.class})
@Slf4j
@MapperScan(basePackages = "com.zzx.sharding.mapper", sqlSessionTemplateRef = "sqlSessionTemplate")
public class DataSourceConfig {

    /**
     * 配置数据源0，数据源的名称最好要有一定的规则，方便配置分库的计算规则
     *
     * @return
     */
    @Bean(name = "master0")
    public DataSource master0(Master0Prop masterProp) {
        Map<String, Object> dsMap = new HashMap<>();
        dsMap.put("type", masterProp.getType());
        dsMap.put("url", masterProp.getUrl());
        dsMap.put("username", masterProp.getUsername());
        dsMap.put("password", masterProp.getPassword());
        dsMap.put("driverClassName", masterProp.getDriverClassName());
        DruidDataSource ds = (DruidDataSource) DataSourceUtil.buildDataSource(dsMap);
        // 每个分区最大的连接数
        ds.setMaxActive(20);
        // 每个分区最小的连接数
        ds.setMinIdle(5);
        return ds;
    }

    /**
     * 配置数据源0，数据源的名称最好要有一定的规则，方便配置分库的计算规则
     *
     * @return
     */
    @Bean(name = "master1")
    public DataSource master1(Master1Prop masterProp) {
        Map<String, Object> dsMap = new HashMap<>();
        dsMap.put("type", masterProp.getType());
        dsMap.put("url", masterProp.getUrl());
        dsMap.put("username", masterProp.getUsername());
        dsMap.put("password", masterProp.getPassword());
        dsMap.put("driverClassName", masterProp.getDriverClassName());
        DruidDataSource ds = (DruidDataSource) DataSourceUtil.buildDataSource(dsMap);
        // 每个分区最大的连接数
        ds.setMaxActive(20);
        // 每个分区最小的连接数
        ds.setMinIdle(5);
        return ds;
    }


    /**
     * 配置数据源0，数据源的名称最好要有一定的规则，方便配置分库的计算规则
     *
     * @return
     */
    @Bean(name = "slave0")
    public DataSource slave0(Slave0Prop slave0Prop) {
        Map<String, Object> dsMap = new HashMap<>();
        dsMap.put("type", slave0Prop.getType());
        dsMap.put("url", slave0Prop.getUrl());
        dsMap.put("username", slave0Prop.getUsername());
        dsMap.put("password", slave0Prop.getPassword());
        dsMap.put("driverClassName", slave0Prop.getDriverClassName());

        DruidDataSource ds = (DruidDataSource) DataSourceUtil.buildDataSource(dsMap);
        // 每个分区最大的连接数
        ds.setMaxActive(20);
        // 每个分区最小的连接数
        ds.setMinIdle(5);
        return  ds;
    }

    /**
     * 配置数据源0，数据源的名称最好要有一定的规则，方便配置分库的计算规则
     *
     * @return
     */
    @Bean(name = "slave1")
    public DataSource slave1(Slave1Prop slave1Prop) {
        Map<String, Object> dsMap = new HashMap<>();
        dsMap.put("type", slave1Prop.getType());
        dsMap.put("url", slave1Prop.getUrl());
        dsMap.put("username", slave1Prop.getUsername());
        dsMap.put("password", slave1Prop.getPassword());
        dsMap.put("driverClassName", slave1Prop.getDriverClassName());

        DruidDataSource ds = (DruidDataSource) DataSourceUtil.buildDataSource(dsMap);
        // 每个分区最大的连接数
        ds.setMaxActive(20);
        // 每个分区最小的连接数
        ds.setMinIdle(5);
        return  ds;
    }

    @Bean("dataSource")
    public DataSource dataSource(@Qualifier("master0") DataSource master0, @Qualifier("master1") DataSource master1, @Qualifier("slave0") DataSource slave0, @Qualifier("slave1") DataSource slave1) throws SQLException {

        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("master0", master0);
        dataSourceMap.put("master1", master1);
        dataSourceMap.put("slave0", slave0);
        dataSourceMap.put("slave1", slave1);

        List<String> slave0List = new ArrayList<>();
        slave0List.add("slave0");
        List<String> slave1List = new ArrayList<>();
        slave1List.add("slave1");

        // 主从策略
        LoadBalanceStrategyConfiguration loadBalanceStrategyConfiguration = new LoadBalanceStrategyConfiguration("round_robin");
        MasterSlaveRuleConfiguration master0SlaveRuleConfiguration = new MasterSlaveRuleConfiguration("master0", "master0", slave0List, loadBalanceStrategyConfiguration);
        MasterSlaveRuleConfiguration master1SlaveRuleConfiguration = new MasterSlaveRuleConfiguration("master1", "master1", slave1List, loadBalanceStrategyConfiguration);

        // 打开shardingsphere sql日志
        Properties properties = new Properties();
        properties.setProperty("sql.show", Boolean.TRUE.toString());

        // 配置分片规则 分库分表 读写分离
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getMasterSlaveRuleConfigs().add(master0SlaveRuleConfiguration);
        shardingRuleConfig.getMasterSlaveRuleConfigs().add(master1SlaveRuleConfiguration);
        // 配置消息表分库分表
        shardingRuleConfig.getTableRuleConfigs().add(getImTeamMessageRuleConfiguration());
        // 获取数据源对象
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, properties);
        return dataSource;
    }

    /** 消息表——im_team_message */
    TableRuleConfiguration getImTeamMessageRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration("im_team_message", "master$->{0..1}.im_team_message$->{0..2}");
        result.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("team_id", "master$->{team_id % 2}"));
        result.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("team_msg_id", "im_team_message$->{team_msg_id % 3}"));
        result.setKeyGeneratorConfig(new KeyGeneratorConfiguration("SNOWFLAKE", "id"));
        return result;
    }
    /** 消息表——im_team_message */

    @Bean
    public DataSourceTransactionManager shardTransactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean("sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));
        return bean.getObject();
    }

    @Bean("sqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);


    }
}
