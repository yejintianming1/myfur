package com.wu.fur.es.boot.sharding;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.shardingses.sharding")
public class ShardingRuleConfigurationProperties {

    private Map<String, ShardingTableRuleConfiguration> tables = new LinkedHashMap<>();

}
