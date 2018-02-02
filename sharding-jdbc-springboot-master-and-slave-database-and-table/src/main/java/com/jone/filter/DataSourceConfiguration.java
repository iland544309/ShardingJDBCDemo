package com.jone.filter;


import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.google.common.collect.Lists;
import com.jone.algorithm.DataBaseTimeStampAlgorithm;
import com.jone.algorithm.TableTimeStampAlgorithm;
import com.jone.utils.database.DruidDataSourceUtil;
import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.config.MasterSlaveRuleConfiguration;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.api.config.strategy.StandardShardingStrategyConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by iland on 2017/12/20.
 */

@Configuration
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class DataSourceConfiguration implements EnvironmentAware {

    private static final Logger log = LoggerFactory.getLogger(DataSourceConfiguration.class);

    private RelaxedPropertyResolver propertyResolver;

    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment);
    }

    @Bean
    public DataSource getShardingDataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        //数据库分库策略
//        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration(
//                "user_id", DataBaseTimeStampAlgorithm.class.getName()
//        ));
        //数据表分表策略
        shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
        shardingRuleConfig.getBindingTableGroups().add("t_order");
        //主从库策略
        shardingRuleConfig.setMasterSlaveRuleConfigs(getMasterSlaveRuleConfigurations());
        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, new HashMap<String, Object>(), new Properties());
    }


    private static Map<String, DataSource> createDataSourceMap() {
        final Map<String, DataSource> result = new HashMap<String, DataSource>(6, 1);
        result.put("demo_ds_master_0",          new DruidDataSourceUtil().createDataSource("sharding.jdbc.datasource.demo_ds_master_0"));
        result.put("demo_ds_master_0_slave_0",  new DruidDataSourceUtil().createDataSource("sharding.jdbc.datasource.demo_ds_master_0_slave_0"));
        //result.put("demo_ds_master_0_slave_1",  new DruidDataSourceUtil().createDataSource("sharding.jdbc.datasource.demo_ds_master_0_slave_1"));

        result.put("demo_ds_master_1",          new DruidDataSourceUtil().createDataSource("sharding.jdbc.datasource.demo_ds_master_1"));
        result.put("demo_ds_master_1_slave_0",  new DruidDataSourceUtil().createDataSource("sharding.jdbc.datasource.demo_ds_master_1_slave_0"));
        //result.put("demo_ds_master_1_slave_1",  new DruidDataSourceUtil().createDataSource("sharding.jdbc.datasource.demo_ds_master_1_slave_1"));

        return result;
    }


    private List<MasterSlaveRuleConfiguration> getMasterSlaveRuleConfigurations(){
        MasterSlaveRuleConfiguration masterSlaveRuleConfig1 = new MasterSlaveRuleConfiguration();
        masterSlaveRuleConfig1.setName("ds_0");
        masterSlaveRuleConfig1.setMasterDataSourceName("demo_ds_master_0");
        //masterSlaveRuleConfig1.setSlaveDataSourceNames(Arrays.asList("demo_ds_master_0_slave_0", "demo_ds_master_0_slave_1"));
        masterSlaveRuleConfig1.setSlaveDataSourceNames(Arrays.asList("demo_ds_master_0_slave_0"));

        MasterSlaveRuleConfiguration masterSlaveRuleConfig2 = new MasterSlaveRuleConfiguration();
        masterSlaveRuleConfig2.setName("ds_1");
        masterSlaveRuleConfig2.setMasterDataSourceName("demo_ds_master_1");
        //masterSlaveRuleConfig2.setSlaveDataSourceNames(Arrays.asList("demo_ds_master_1_slave_0", "demo_ds_master_1_slave_1"));
        masterSlaveRuleConfig2.setSlaveDataSourceNames(Arrays.asList("demo_ds_master_1_slave_0"));
        return Lists.newArrayList(masterSlaveRuleConfig1, masterSlaveRuleConfig2);
    }



    private TableRuleConfiguration getOrderTableRuleConfiguration() {
        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration();
        orderTableRuleConfig.setLogicTable("t_order");
        orderTableRuleConfig.setActualDataNodes("ds_${0..1}.t_order_${[201801, 201802, 201803, 201804, 201805, 201806, 201807, 201808, 201809, 201810, 201811, 201812]}");
        orderTableRuleConfig.setKeyGeneratorColumnName("order_id");

        System.out.println("start load table rule config");
        orderTableRuleConfig.setTableShardingStrategyConfig(
                new StandardShardingStrategyConfiguration(
                        "add_time", TableTimeStampAlgorithm.class.getName()
                )
        );
        System.out.println("end load table rule config");
        return orderTableRuleConfig;
    }




    @Bean
    public ServletRegistrationBean druidServlet() {
        log.info("init Druid Servlet Configuration ");

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new StatViewServlet());

        servletRegistrationBean.addUrlMappings("/druid/*");
        Map<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("loginUsername", "admin");// 用户名
        initParameters.put("loginPassword", "admin");// 密码
        initParameters.put("resetEnable", "false");// 禁用HTML页面上的“Reset All”功能
        initParameters.put("allow", ""); // IP白名单 (没有配置或者为空，则允许所有访问)
        //initParameters.put("deny", "192.168.20.38");// IP黑名单 (存在共同时，deny优先于allow)
        servletRegistrationBean.setInitParameters(initParameters);
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

}

