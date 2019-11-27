package com.wu.fur.es.boot.client;


/**
 * 路由分片算法
 */
public interface EsRoutingAlgorithm {

    String doSharding(Integer shards,String shardingValue);
}
