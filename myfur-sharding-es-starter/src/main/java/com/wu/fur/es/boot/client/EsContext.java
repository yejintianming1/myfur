package com.wu.fur.es.boot.client;

import java.util.Map;

public interface EsContext {

    Map<String, EsDataSource> getDataSourceMap();

    Map<String, EsShadingTableRule> getRuleMap();

    EsDataSource getDefaultDataSource();

    void close();

}
