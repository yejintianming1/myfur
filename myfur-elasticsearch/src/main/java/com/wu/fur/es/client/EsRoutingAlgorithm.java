package com.wu.fur.es.client;

/**
 * ES路由算法解耦
 */
public interface EsRoutingAlgorithm {

    public String doRouting(String routingFieldValue);
}
