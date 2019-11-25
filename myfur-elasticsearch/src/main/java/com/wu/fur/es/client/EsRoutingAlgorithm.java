package com.wu.fur.es.client;

/**
 * ES路由算法接口
 */
public interface EsRoutingAlgorithm {

    String doRouting(EsSourceConfig config,String routingFieldValue);
}
