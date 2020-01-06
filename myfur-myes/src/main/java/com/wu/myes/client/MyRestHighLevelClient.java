package com.wu.myes.client;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 对原生的client做扩展
 */
public class MyRestHighLevelClient extends RestHighLevelClient{


    public MyRestHighLevelClient(RestClientBuilder restClientBuilder) {
        super(restClientBuilder);
    }

    public <T> List<T> search(T t) throws IOException {
        SearchRequest searchRequest = new SearchRequest("nabs_user_info0");

        SearchResponse search = super.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = search.getHits();
        if (hits != null) {
            List<T> list = new ArrayList<T>();
            for (SearchHit searchHit : hits) {
                Map<String, Object> sourceMap = searchHit.getSourceAsMap();
                String sourceString = JSONObject.toJSONString(sourceMap);
                Class<T> aClass = (Class<T>) t.getClass();
                T entity = JSONObject.parseObject(sourceString, aClass);
                list.add(entity);
            }
            return list;
        }
        return null;
    }

}
