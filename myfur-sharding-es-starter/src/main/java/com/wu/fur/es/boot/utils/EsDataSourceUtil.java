package com.wu.fur.es.boot.utils;

import com.wu.fur.es.boot.client.EsRestDataSource;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class EsDataSourceUtil {

    public static EsRestDataSource builder(Map<String, Object> dataSourceProps) {
        EsRestDataSource esRestDataSource = new EsRestDataSource();
        String hostPorts = dataSourceProps.get("host").toString();
        //hostPort处理
        String[] hostPort = hostPorts.split(",");
        List<HttpHost> hostList = new ArrayList<>();
        for (int j = 0; j < hostPort.length; j++) {
            String[] hp = hostPort[j].split( ":");
            String host = hp[0];
            String port = hp[1];
            //构建HttpPort对象
            HttpHost httpHost = new HttpHost(host,Integer.valueOf(port));
            hostList.add(httpHost);
        }
        esRestDataSource.setHttpHosts(hostList.toArray(new HttpHost[hostList.size()]));
        String connectTimeout = dataSourceProps.get("connect-timeout").toString();
        String socketTimeout = dataSourceProps.get("socket-timeout").toString();
        String requestTimeout = dataSourceProps.get("request-timeout").toString();
        String maxConnectNum = dataSourceProps.get("max-connect-num").toString();
        String maxConnectRoute = dataSourceProps.get("max-connect-route").toString();
        String indexNodes = dataSourceProps.get("index-nodes").toString();
        String shards = dataSourceProps.get("shards").toString();
        String replicas = dataSourceProps.get("replicas").toString();

        esRestDataSource.setConnectTimeout(Long.valueOf(connectTimeout));
        esRestDataSource.setSocketTimeout(Long.valueOf(socketTimeout));
        esRestDataSource.setRequestTimeout(Long.valueOf(requestTimeout));
        esRestDataSource.setMaxConnectNum(Long.valueOf(maxConnectNum));
        esRestDataSource.setMaxConnectRoute(Long.valueOf(maxConnectRoute));
        esRestDataSource.setIndexArr(Arrays.asList(indexNodes.split(",")));
        esRestDataSource.setShards(Long.valueOf(shards));
        esRestDataSource.setReplicas(Long.valueOf(replicas));

                RestClientBuilder builder = RestClient.builder(esRestDataSource.getHttpHosts());
        builder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {

            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder builder) {
                builder.setConnectTimeout(esRestDataSource.getConnectTimeout().intValue());
                builder.setSocketTimeout(esRestDataSource.getSocketTimeout().intValue());
                builder.setConnectionRequestTimeout(esRestDataSource.getRequestTimeout().intValue());
                return builder;
            }
        });

        builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
                httpAsyncClientBuilder.setMaxConnTotal(esRestDataSource.getMaxConnectNum().intValue());
                httpAsyncClientBuilder.setMaxConnPerRoute(esRestDataSource.getMaxConnectRoute().intValue());
                return httpAsyncClientBuilder;
            }
        });

        RestHighLevelClient client = new RestHighLevelClient(builder);
        esRestDataSource.setClient(client);
        return esRestDataSource;
    }
}
