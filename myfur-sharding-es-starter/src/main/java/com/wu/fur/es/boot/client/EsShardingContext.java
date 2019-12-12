package com.wu.fur.es.boot.client;


import java.io.IOException;
import java.util.Map;

public class EsShardingContext implements EsContext {

    private Map<String, EsDataSource> dataSourceMap;
    private EsDataSource defaultDataSource;
    private Map<String, EsShadingTableRule> ruleMap;

    public EsShardingContext(Map<String, EsDataSource> dataSourceMap, EsDataSource defaultDataSource, Map<String, EsShadingTableRule> ruleMap) {
        this.dataSourceMap = dataSourceMap;
        this.defaultDataSource = defaultDataSource;
        this.ruleMap = ruleMap;
    }

    @Override
    public Map<String, EsDataSource> getDataSourceMap() {
        return dataSourceMap;
    }

    public void setDataSourceMap(Map<String, EsDataSource> dataSourceMap) {
        this.dataSourceMap = dataSourceMap;
    }

    @Override
    public EsDataSource getDefaultDataSource() {
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
