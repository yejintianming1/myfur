package com.wu.fur.es.boot.client;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.List;

public interface EsDataSource {

    HttpHost[] getHttpHosts();

    HttpHost[] buildHttpHosts();

    List<String> getActualIndexNodes();

    List<String> buildActualIndexNodes();

    RestHighLevelClient getClient();

    RestHighLevelClient buildClient();

    Long getShards();
}
