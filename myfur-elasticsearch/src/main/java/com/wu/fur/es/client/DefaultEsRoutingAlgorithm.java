package com.wu.fur.es.client;

public class DefaultEsRoutingAlgorithm implements EsRoutingAlgorithm{
    @Override
    public String doRouting(String routingFieldValue) {
        return routingFieldValue;
    }
}
