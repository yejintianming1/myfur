package com.wu.fur.es.boot.client;

import lombok.Data;

import java.util.Collection;
import java.util.Map;

/**
 * ES数据源配置对象
 */
@Data
public class EsSourceConfig {

    private String indexField;//索引分片字段

    private Collection<String> indexNodes;//索引源集合

    private String routingField;//路由分片字段

    private Collection<String> routingNodes;//路由源集合

    private Map<String,String> logicIndexMap;//key=逻辑索引名， value=数据源[组]名

    private Map<String, EsRestDataSource> sourceMap;//数据源key=数据源名，value=数据源对象


//    private HttpHost[] httpHosts;
//    private int connectTimeout = 10000;
//    private int socketTimeout = 10000;
//    private int requestTimeout = 10000;
//    private int maxConnectNum = 100;
//    private int maxConnectRoute = 100;
//    private String indexGroupName;
//    private Integer indexGroupNum;
//    private String[] indexGroupNodes;
//    private String indexField;//索引字段
////    private String index;
//    private String type = "_doc";
//    private int shards = 10;//分片
//    private int replicas = 20;//副本
//    private String routingField;//路由字段
////    private EsRoutingAlgorithm esRoutingAlgorithm;//路由算法类
////    private EsIndexAlgorithm esIndexAlgorithm;//索引组算法类
//    private int indexMode = 0;//索引模式:0-单索引模式；1-索引组模式

}
