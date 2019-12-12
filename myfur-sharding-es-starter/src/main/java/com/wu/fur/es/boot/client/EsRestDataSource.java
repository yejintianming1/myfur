package com.wu.fur.es.boot.client;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EsRestDataSource extends EsRestConfig implements EsDataSource{

    private HttpHost[] httpHosts;;//连接的socket信息
    private List<String> actualIndexNodes;//真实的索引族
    private RestHighLevelClient client;


    @Override
    public HttpHost[] getHttpHosts() {
        return httpHosts;
    }

    @Override
    public HttpHost[] buildHttpHosts() {
        String hostPorts = getHost();
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
        this.httpHosts = hostList.toArray(new HttpHost[hostList.size()]);
        return httpHosts;
    }

    @Override
    public List<String> getActualIndexNodes() {
        return actualIndexNodes;
    }

    @Override
    public List<String> buildActualIndexNodes() {
        String indexNodes = getIndexNodes();
        this.actualIndexNodes = Arrays.asList(indexNodes.split(","));
        return actualIndexNodes;
    }

    @Override
    public RestHighLevelClient getClient() {
        return client;
    }

    @Override
    public RestHighLevelClient buildClient() {
        RestClientBuilder builder = RestClient.builder(getHttpHosts());
        builder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {

            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder builder) {
                builder.setConnectTimeout(getConnectTimeout().intValue());
                builder.setSocketTimeout(getSocketTimeout().intValue());
                builder.setConnectionRequestTimeout(getRequestTimeout().intValue());
                return builder;
            }
        });

        builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
                httpAsyncClientBuilder.setMaxConnTotal(getMaxConnectNum().intValue());
                httpAsyncClientBuilder.setMaxConnPerRoute(getMaxConnectRoute().intValue());
                return httpAsyncClientBuilder;
            }
        });

        RestHighLevelClient client = new RestHighLevelClient(builder);
        this.client = client;
        return client;
    }
}
