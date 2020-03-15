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
@EnableConfigurationProperties({MasterProp.class, Slave0Prop.class, Slave1Prop.class})
@Slf4j
@MapperScan(basePackages = "com.zzx.sharding.mapper", sqlSessionTemplateRef = "sqlSessionTemplate")
public class DataSourceConfig {

    /**
     * 配置数据源0，数据源的名称最好要有一定的规则，方便配置分库的计算规则
     *
     * @return
     */
    @Bean(name = "master")
    public DataSource master(MasterProp masterProp) {
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
    public DataSource dataSource(@Qualifier("master") DataSource master, @Qualifier("slave0") DataSource slave0, @Qualifier("slave1") DataSource slave1) throws SQLException {

        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("master", master);
        dataSourceMap.put("slave0", slave0);
        dataSourceMap.put("slave1", slave1);

        List<String> slaveList = new ArrayList<>();
        slaveList.add("slave0");
        slaveList.add("slave1");

        // 从库负载策略
        LoadBalanceStrategyConfiguration loadBalanceStrategyConfiguration = new LoadBalanceStrategyConfiguration("round_robin");
        MasterSlaveRuleConfiguration masterSlaveRuleConfiguration = new MasterSlaveRuleConfiguration("ms", "master", slaveList, loadBalanceStrategyConfiguration);

        // 打开shardingsphere sql日志
        Properties properties = new Properties();
        properties.setProperty("sql.show", Boolean.TRUE.toString());

        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();

        //TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration("t_order", "ds${0..1}.t_order_${0..1}");
        // 配置分库 + 分表策略
        //orderTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds${user_id % 2}"));
        //orderTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "t_order_${order_id % 2}"));
        //shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);

        shardingRuleConfig.getMasterSlaveRuleConfigs().add(masterSlaveRuleConfiguration);
        shardingRuleConfig.getTableRuleConfigs().add(getImTeamMessageRuleConfiguration());
        // 获取数据源对象
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, properties);
        return dataSource;
    }

    /** 消息表——im_team_message */
    TableRuleConfiguration getImTeamMessageRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration("im_team_message");
        result.setKeyGeneratorConfig(getImTeamMessageKeyGeneratorConfiguration());
        return result;
    }
    private static KeyGeneratorConfiguration getImTeamMessageKeyGeneratorConfiguration() {
        KeyGeneratorConfiguration result = new KeyGeneratorConfiguration("SNOWFLAKE", "id");
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
