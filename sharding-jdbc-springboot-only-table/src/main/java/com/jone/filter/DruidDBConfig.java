package com.jone.filter;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by iland on 2018/2/1.
 */

@Configuration
public class DruidDBConfig {
    private Logger logger = LoggerFactory.getLogger(DruidDBConfig.class);

    @Value("${sharding.jdbc.datasource.ds.url}")
    private String dbUrl;

    @Value("${sharding.jdbc.datasource.ds.username}")
    private String username;

    @Value("${sharding.jdbc.datasource.ds.password}")
    private String password;

    @Value("${sharding.jdbc.datasource.ds.driverClassName}")
    private String driverClassName;

    @Value("${sharding.jdbc.datasource.ds.initialSize}")
    private int initialSize;

    @Value("${sharding.jdbc.datasource.ds.minIdle}")
    private int minIdle;

    @Value("${sharding.jdbc.datasource.ds.maxActive}")
    private int maxActive;

    @Value("${sharding.jdbc.datasource.ds.maxWait}")
    private int maxWait;

    @Value("${sharding.jdbc.datasource.ds.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${sharding.jdbc.datasource.ds.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${sharding.jdbc.datasource.ds.validationQuery}")
    private String validationQuery;

    @Value("${sharding.jdbc.datasource.ds.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${sharding.jdbc.datasource.ds.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${sharding.jdbc.datasource.ds.testOnReturn}")
    private boolean testOnReturn;

    @Value("${sharding.jdbc.datasource.ds.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${sharding.jdbc.datasource.ds.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${sharding.jdbc.datasource.ds.filters}")
    private String filters;

    @Value("{sharding.jdbc.datasource.ds.connectionProperties}")
    private String connectionProperties;



    public DataSource dataSource(){
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        System.out.println("druidDataSource.username:" + this.username);
        System.out.println("druidDataSource.password:" + this.password);
        //configuration
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            logger.error("druid configuration initialization filter", e);
        }
        datasource.setConnectionProperties(connectionProperties);

        return datasource;
    }

}
