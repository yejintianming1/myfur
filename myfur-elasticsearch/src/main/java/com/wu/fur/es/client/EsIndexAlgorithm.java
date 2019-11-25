package com.wu.fur.es.client;

/**
 * ES索引组算法接口
 */
public interface EsIndexAlgorithm {

    String doIndex(EsSourceConfig config,String indexFieldValue);
}
