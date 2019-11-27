package com.wu.fur.es.boot.client;

import lombok.Data;

/**
 * 分片规则
 */
@Data
public class EsShadingTableRule {

//    private String[] actualDataNodes;
    private String actualDataNode;

    private String indexColumn;

    private EsIndexAlgorithm esIndexAlgorithm;

    private String routingColumn;

    private EsRoutingAlgorithm esRoutingAlgorithm;

}
