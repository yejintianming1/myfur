package com.wu.myes.test;

import com.alibaba.fastjson.JSON;
import com.wu.myes.session.DefaultEsSession;
import com.wu.myes.session.EsSession;
import com.wu.myes.session.MyEsSession;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import javax.jws.soap.SOAPBinding;
import java.util.List;

public class EsTest {

    public static void main(String[] args) throws Exception {

        

        RestClientBuilder builder = RestClient.builder(getHttpHosts());
        builder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {

            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder builder) {
                builder.setConnectTimeout(getConnectTimeout());
                builder.setSocketTimeout(getSocketTimeout());
                builder.setConnectionRequestTimeout(getRequestTimeout());
                return builder;
            }
        });

        builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
                httpAsyncClientBuilder.setMaxConnTotal(getMaxConnectNum());
                httpAsyncClientBuilder.setMaxConnPerRoute(getMaxConnectRoute());
                return httpAsyncClientBuilder;
            }
        });


        EsSession esSession = new MyEsSession(builder);
//        EsSession esSession = new DefaultEsSession(builder);
        User u = new User();
//        SearchResponse search = esSession.search(u);
        List<User> search = esSession.searchT(u);
        System.out.println(JSON.toJSONString(search));

        System.out.println("aaaaaaaa");
    }

    private static int getMaxConnectRoute() {
        return 100;
    }

    private static int getMaxConnectNum() {
        return 100;
    }

    private static int getRequestTimeout() {
        return 10000;
    }

    private static int getSocketTimeout() {
        return 10000;
    }

    private static int getConnectTimeout() {
        return 10000;
    }

    private static HttpHost[] getHttpHosts() {
        HttpHost[] httpHosts = new HttpHost[]{new HttpHost("10.130.36.239",9200)};
        return httpHosts;
    }


}
