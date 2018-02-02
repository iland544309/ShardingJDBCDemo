package com;

import com.jone.utils.database.DruidDataSourceUtil;
import com.jone.utils.properties.ReadDruidProperties;

/**
 * Created by iland on 2018/2/2.
 */
public class app {
    public static void main(String[] args) {
        //System.out.println(ReadDruidProperties.readProperty2String("sharding.jdbc.datasource.ds_master", "url"));
        //System.out.println(ReadDruidProperties.readProperty2String("sharding.jdbc.datasource.ds_master", "username"));

        DruidDataSourceUtil druidDataSourceUtil = new DruidDataSourceUtil();
        druidDataSourceUtil.createDataSource("sharding.jdbc.datasource.ds_master");
    }
}
