package com.jone.utils.database;

import org.slf4j.Logger;
import javax.sql.DataSource;
import java.sql.SQLException;
import org.slf4j.LoggerFactory;
import com.alibaba.druid.pool.DruidDataSource;
import com.jone.utils.properties.ReadDruidProperties;

/**
 * Created by iland on 2018/2/1.
 */

public class DruidDataSourceUtil {

    private Logger logger = LoggerFactory.getLogger(DruidDataSourceUtil.class);

    public DataSource createDataSource(String dataSourceConfigPrefix){
        DruidDataSource datasource = new DruidDataSource();
        this.setConfigPrefix(dataSourceConfigPrefix);

        datasource.setUrl(this.getDbUrl());
        datasource.setUsername(this.getUsername());
        datasource.setPassword(this.getPassword());
        datasource.setDriverClassName(this.getDriverClassName());
        System.out.println("druidDataSource.username:" + this.getUsername());
        System.out.println("druidDataSource.password:" + this.getPassword());
        System.out.println("druidDataSource.url:" + this.getDbUrl());
        //configuration
        datasource.setInitialSize(this.getInitialSize());
        datasource.setMinIdle(this.getMinIdle());
        datasource.setMaxActive(this.getMaxActive());
        datasource.setMaxWait(this.getMaxWait());
        datasource.setTimeBetweenEvictionRunsMillis(this.getTimeBetweenEvictionRunsMillis());
        datasource.setMinEvictableIdleTimeMillis(this.getMinEvictableIdleTimeMillis());
        datasource.setValidationQuery(this.getValidationQuery());
        datasource.setTestWhileIdle(this.isTestWhileIdle());
        datasource.setTestOnBorrow(this.isTestOnBorrow());
        datasource.setTestOnReturn(this.isTestOnReturn());
        datasource.setPoolPreparedStatements(this.isPoolPreparedStatements());
        datasource.setMaxPoolPreparedStatementPerConnectionSize(this.getMaxPoolPreparedStatementPerConnectionSize());
        try {
            datasource.setFilters(this.getFilters());
        } catch (SQLException e) {
            logger.error("druid configuration initialization filter", e);
        }
        datasource.setConnectionProperties(this.getConnectionProperties());

        return datasource;
    }


    public String getDbUrl() {
        return ReadDruidProperties.readProperty2String(getConfigPrefix(), "url");
    }

    public String getUsername() {
        return ReadDruidProperties.readProperty2String(getConfigPrefix(), "username");
    }

    public String getPassword() {
        return ReadDruidProperties.readProperty2String(getConfigPrefix(), "password");
    }

    public String getDriverClassName() {
        return ReadDruidProperties.readProperty2String(getConfigPrefix(), "driverClassName");
    }

    public int getInitialSize() {
        return ReadDruidProperties.readProperty2Int(getConfigPrefix(), "initialSize", 5);
    }

    public int getMinIdle() {
        return ReadDruidProperties.readProperty2Int(getConfigPrefix(), "minIdle", 5);
    }

    public int getMaxActive() {
        return ReadDruidProperties.readProperty2Int(getConfigPrefix(), "maxActive", 20);
    }

    public int getMaxWait() {
        return ReadDruidProperties.readProperty2Int(getConfigPrefix(), "maxWait", 60000);
    }

    public int getTimeBetweenEvictionRunsMillis() {
        return ReadDruidProperties.readProperty2Int(getConfigPrefix(), "timeBetweenEvictionRunsMillis", 60000);
    }

    public int getMinEvictableIdleTimeMillis() {
        return ReadDruidProperties.readProperty2Int(getConfigPrefix(), "minEvictableIdleTimeMillis", 300000);
    }

    public String getValidationQuery() {
        return ReadDruidProperties.readProperty2String(getConfigPrefix(), "validationQuery");
    }

    public boolean isTestWhileIdle() {
        return ReadDruidProperties.readProperty2Bool(getConfigPrefix(), "testWhileIdle", true);
    }

    public boolean isTestOnBorrow() {
        return ReadDruidProperties.readProperty2Bool(getConfigPrefix(), "testOnBorrow", false);
    }

    public boolean isTestOnReturn() {
        return ReadDruidProperties.readProperty2Bool(getConfigPrefix(), "testOnReturn", false);
    }

    public boolean isPoolPreparedStatements() {
        return ReadDruidProperties.readProperty2Bool(getConfigPrefix(), "poolPreparedStatements", true);
    }

    public int getMaxPoolPreparedStatementPerConnectionSize() {
        return ReadDruidProperties.readProperty2Int(getConfigPrefix(), "maxPoolPreparedStatementPerConnectionSize", 20);
    }

    public String getFilters() {
        return ReadDruidProperties.readProperty2String(getConfigPrefix(), "filters");
    }

    public String getConnectionProperties() {
        return ReadDruidProperties.readProperty2String(getConfigPrefix(), "connectionProperties");
    }



    public String getConfigPrefix() {
        return configPrefix;
    }

    public void setConfigPrefix(String configPrefix) {
        this.configPrefix = configPrefix;
    }

    private String configPrefix;


    /*
    private String dbUrl;


    private String username;


    private String password;


    private String driverClassName;


    private int initialSize;



    private int minIdle;



    private int maxActive;



    private int maxWait;


    private int timeBetweenEvictionRunsMillis;


    private int minEvictableIdleTimeMillis;


    private String validationQuery;


    private boolean testWhileIdle;


    private boolean testOnBorrow;


    private boolean testOnReturn;


    private boolean poolPreparedStatements;


    private int maxPoolPreparedStatementPerConnectionSize;


    private String filters;


    private String connectionProperties;

    */

}
