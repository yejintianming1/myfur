package com.wu.fur.es.boot.client;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EsRestConfig {

    private String host;//连接的socket信息
    private Long connectTimeout;//连接超时时间毫秒
    private Long socketTimeout;//socket超时时间
    private Long requestTimeout;//request超时时间
    private Long maxConnectNum;
    private Long maxConnectRoute;
    private String indexNodes;//真实的索引族
    private Long shards;//分片数
    private Long replicas;//副本数


}
