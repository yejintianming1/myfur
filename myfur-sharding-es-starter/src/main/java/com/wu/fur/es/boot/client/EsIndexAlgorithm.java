package com.wu.fur.es.boot.client;

import java.util.Collection;

/**
 * 索引算法类
 */
public interface EsIndexAlgorithm {

    String doSharding(Collection<String> collection,String shardingValue);
}
