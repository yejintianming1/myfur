package com.wu.fur.es.boot.client;


import java.io.IOException;
import java.util.Map;

public class EsShardingContext implements EsContext {

    private Map<String, EsRestDataSource> dataSourceMap;
    private EsRestDataSource defaultDataSource;
    private Map<String, EsShadingTableRule> ruleMap;

    public EsShardingContext(Map<String, EsRestDataSource> dataSourceMap, EsRestDataSource defaultDataSource, Map<String, EsShadingTableRule> ruleMap) {
        this.dataSourceMap = dataSourceMap;
        this.defaultDataSource = defaultDataSource;
        this.ruleMap = ruleMap;
    }

    @Override
    public Map<String, EsRestDataSource> getDataSourceMap() {
        return dataSourceMap;
    }

    public void setDataSourceMap(Map<String, EsRestDataSource> dataSourceMap) {
        this.dataSourceMap = dataSourceMap;
    }

    @Override
    public EsRestDataSource getDefaultDataSource() {
        return defaultDataSource;
    }

    @Override
    public void close() {
        dataSourceMap.forEach((key,value) ->{
            try {
                value.getClient().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void setDefaultDataSource(EsRestDataSource defaultDataSource) {
        this.defaultDataSource = defaultDataSource;
    }

    @Override
    public Map<String, EsShadingTableRule> getRuleMap() {
        return ruleMap;
    }

    public void setRuleMap(Map<String, EsShadingTableRule> ruleMap) {
        this.ruleMap = ruleMap;
    }
}
