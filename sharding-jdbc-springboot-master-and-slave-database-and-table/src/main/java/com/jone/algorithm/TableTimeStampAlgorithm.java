package com.jone.algorithm;

import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;

/**
 * Created by iland on 2018/1/26.
 */
public class TableTimeStampAlgorithm implements PreciseShardingAlgorithm<Timestamp> {
    @Override
    public String doSharding(Collection<String> tableNames, PreciseShardingValue<Timestamp> shardingValue) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
        String timeStr = shardingValue.getValue().toString().replace("-", "").substring(0, 6);
        //String timeStr = "";
        System.out.println("timeStr -> " + timeStr);

        for (String each : tableNames) {
            System.out.println("tableName: " + each + " -> " + shardingValue.getValue());

            //if (each.endsWith(simpleDateFormat.format(new Timestamp(System.currentTimeMillis())))) {
            if (each.endsWith(timeStr)) {
                System.out.println("return ->" + each);
                return each;
            }
        }
        throw new UnsupportedOperationException();


//        String tableExt = "";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
//        try{
//            tableExt = simpleDateFormat.format(new Timestamp(System.currentTimeMillis()));
//            System.out.println("TablePartitionStrategy=================");
//            System.out.println(tableExt);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        //return "201801";
//
//        for (String each : tableNames) {
//            System.out.println("tableName: " + each + " -> " + shardingValue.getValue());
//            if (each.endsWith(tableExt)) {
//                return each + "_201801";
//            }
//        }
//
//        throw new UnsupportedOperationException();
    }
}
