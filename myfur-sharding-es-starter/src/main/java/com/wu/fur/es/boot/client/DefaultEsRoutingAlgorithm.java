package com.wu.fur.es.boot.client;

public class DefaultEsRoutingAlgorithm implements EsRoutingAlgorithm{
    @Override
    public String doSharding(Integer shards, String shardingValue) {
        return shardingValue;
    }
//    @Override
//    public String doRouting(EsSourceConfig config,String routingFieldValue) {
//        return routingFieldValue;
//    }
}
