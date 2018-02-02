package com.jone.algorithm;

import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

/**
 * Created by iland on 2018/2/1.
 */
public class DataBaseTimeStampAlgorithm implements PreciseShardingAlgorithm<Integer> {
    @Override
    public String doSharding(Collection<String> dataBaseNodes, PreciseShardingValue<Integer> shardingValue) {
        for (String each : dataBaseNodes) {
            System.out.println("DataBaseTimeStampAlgorithm->" + dataBaseNodes);
            int index = shardingValue.getValue() % 2;
            if (each.endsWith(String.valueOf(index))) {
                System.out.println("select database ->" + each);
                return each;
            }
        }
        throw new UnsupportedOperationException();
    }
}
