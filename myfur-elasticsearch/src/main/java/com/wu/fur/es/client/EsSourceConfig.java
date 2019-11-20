package com.wu.fur.es.client;

import lombok.Data;
import org.apache.http.HttpHost;

/**
 * ES数据源配置对象
 */
@Data
public class EsSourceConfig {

    private HttpHost[] httpHosts;
    private int connectTimeout = 10000;
    private int socketTimeout = 10000;
    private int requestTimeout = 10000;
    private int maxConnectNum = 100;
    private int maxConnectRoute = 100;
    private String index;
    private String type = "_doc";
    private int shards = 10;//分片
    private int replicas = 20;//副本

}
