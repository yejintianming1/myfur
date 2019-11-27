package com.wu.fur.es.boot.client;

import java.util.Map;

public interface EsContext {

    Map<String, EsRestDataSource> getDataSourceMap();

    Map<String, EsShadingTableRule> getRuleMap();

    EsRestDataSource getDefaultDataSource();

    void close();

}
