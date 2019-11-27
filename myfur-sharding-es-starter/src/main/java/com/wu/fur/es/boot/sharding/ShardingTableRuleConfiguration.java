package com.wu.fur.es.boot.sharding;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShardingTableRuleConfiguration {

//    private String actualDataNodes;
    private String actualDataNode;

    private String indexColumn;

    private String indexAlgorithmClassName;

    private String routingColumn;

    private String routingAlgorithmClassName;

}
