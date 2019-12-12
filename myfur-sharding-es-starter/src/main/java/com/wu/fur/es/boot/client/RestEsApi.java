package com.wu.fur.es.boot.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class RestEsApi implements EsApi {


    //需要一个算法确定索引
//    private EsIndexAlgorithm esIndexAlgorithm;

    //需要一个算法确定路由
//    private EsRoutingAlgorithm esRoutingAlgorithm;

//    //需要读取配置
//    private EsSourceConfig esSourceConfig;

    @Autowired
    private EsContext esContext;


    /**
     * 索引
     */
    public <T> boolean indexDoc(T t) throws Exception {
        //先根据逻辑表找到数据源
        String logicIndexName = EsReader.readLogicIndexName(t);//获取到逻辑索引名
        Map<String, EsShadingTableRule> ruleMap = esContext.getRuleMap();
        EsShadingTableRule tableRule = ruleMap.get(logicIndexName);
        String actualDataNode = tableRule.getActualDataNode();
        Map<String, EsDataSource> dataSourceMap = esContext.getDataSourceMap();
        EsDataSource esDataSource = dataSourceMap.get(actualDataNode);
        String indexColumn = tableRule.getIndexColumn();
        EsIndexAlgorithm esIndexAlgorithm = tableRule.getEsIndexAlgorithm();
        List<String> indexArr = esDataSource.getActualIndexNodes();
        String routingColumn = tableRule.getRoutingColumn();
        EsRoutingAlgorithm esRoutingAlgorithm = tableRule.getEsRoutingAlgorithm();
        Long shards = esDataSource.getShards();
        RestHighLevelClient client = esDataSource.getClient();

        //前置操作，校验分片字段的值是否存在，如果为空，则报异常
        //调用索引分片算法获取指定索引
        String indexValue = EsReader.readFieldValue(t, indexColumn);
        String index = null;
        if (!StringUtils.isBlank(indexValue)) {
            //再根据提供的索引算法从索引组中筛选出一个或多个[暂不提供支持]索引
            index = esIndexAlgorithm.doSharding(indexArr, indexValue);
        }
        String routingValue = EsReader.readFieldValue(t,routingColumn);
        String routing = null;
        if (!StringUtils.isBlank(routingValue)) {
            routing = esRoutingAlgorithm.doSharding(shards.intValue(), routingColumn);
        }
        return indexDoc(t,client,index,routing);
    }



    private  <T> boolean indexDoc(T t,RestHighLevelClient client,String index,String routing) throws Exception {
        IndexRequest indexRequest = new IndexRequest(
                index,//索引名称
                "_doc");//类型名称

        //==============================提供文档源========================================
        indexRequest.source(JSON.toJSONString(t, SerializerFeature.WriteNullStringAsEmpty), XContentType.JSON);
        if (!StringUtils.isBlank(routing)) {
            indexRequest.routing(routing);//路由值
        }

        //===============================执行====================================
        //同步执行
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        //Index Response
        //返回的IndexResponse允许检索有关执行操作的信息，如下所示：
        String type = indexResponse.getType();
        String id = indexResponse.getId();
        long version = indexResponse.getVersion();
        System.out.println(index);
        System.out.println(type);
        System.out.println(id);
        System.out.println(version);
        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
            return true;
            //处理（如果需要）第一次创建文档的情况
        } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            return true;
            //处理（如果需要）文档被重写的情况
        }
        return false;
    }


    /**
     * 搜索
     */
    public <T,Q,E,O,R,W> EsPagination<T> search(Q terms,E matches,O orTerms,R orMatches,W prefixItems,SortField[] sortFields,EsPage page,Class<T> clazz) throws Exception {
        //先根据逻辑表找到数据源
        String logicIndexName = EsReader.readLogicIndexName(terms);//获取到逻辑索引名
        Map<String, EsShadingTableRule> ruleMap = esContext.getRuleMap();
        EsShadingTableRule tableRule = ruleMap.get(logicIndexName);
        String actualDataNode = tableRule.getActualDataNode();
        Map<String, EsDataSource> dataSourceMap = esContext.getDataSourceMap();
        EsDataSource esDataSource = dataSourceMap.get(actualDataNode);
        String indexColumn = tableRule.getIndexColumn();
        EsIndexAlgorithm esIndexAlgorithm = tableRule.getEsIndexAlgorithm();
        List<String> indexArr = esDataSource.getActualIndexNodes();
        String routingColumn = tableRule.getRoutingColumn();
        EsRoutingAlgorithm esRoutingAlgorithm = tableRule.getEsRoutingAlgorithm();
        Long shards = esDataSource.getShards();
        RestHighLevelClient client = esDataSource.getClient();

        String indexValue = EsReader.readFieldValue(terms, indexColumn);
        String index = !StringUtils.isBlank(indexValue) ? esIndexAlgorithm.doSharding(indexArr, indexValue) : null;
        String routingValue = EsReader.readFieldValue(terms,routingColumn);
        String routing = !StringUtils.isBlank(routingValue) ? esRoutingAlgorithm.doSharding(shards.intValue(), routingValue) : null;

        if (StringUtils.isBlank(index)) {
            //TODO 此处可以改造成多线程查询，或者优化成策略模式
            for (String item: indexArr
                 ) {
                index = item;
                EsPagination<T> result = search(terms, matches, orTerms, orMatches, prefixItems, sortFields, page, client, index, routing, clazz);
                if (result.getData() != null) {
                    return result;
                }
            }
        }
        return search(terms,matches,orTerms,orMatches,prefixItems,sortFields,page,client,index,routing,clazz);
    }




    private <T,Q,E,O,R,W> EsPagination<T> search(Q terms,E matches,O orTerms,R orMatches,W prefixItems,SortField[] sortFields,EsPage page,RestHighLevelClient client,String index,String routing,Class<T> clazz) throws Exception {
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types("_doc");
        if (!StringUtils.isBlank(routing)) {
            searchRequest.routing(routing);
        }
        SearchSourceBuilder sb = new SearchSourceBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();//构建复合查询
        String termsStr = JSONObject.toJSONString(terms);
        Map<String, Object> termsMap = JSON.parseObject(termsStr);
        if (termsMap != null) {
            Set<String> set = termsMap.keySet();
            for (String key : set) {
                TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(key, termsMap.get(key));//查询条件不进行分词
                boolQuery.must(termQueryBuilder);//AND,条件之间求交集
            }
        }
        String matchesStr = JSONObject.toJSONString(matches);
        Map<String, Object> matchesMap = JSON.parseObject(matchesStr);
        if (matchesMap != null) {
            Set<String> set = termsMap.keySet();
            for (String key : set) {
                MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(key, matchesMap.get(key));//查询条件进行分词；eg:如果fieldValue是："read book",假设会被分词成read和book
                boolQuery.must(matchQueryBuilder);//AND,条件之间求交集
            }
        }
        String orTermsStr = JSONObject.toJSONString(orTerms);
        Map<String, Object> orTermsMap = JSON.parseObject(orTermsStr);
        if (orTermsMap != null) {
            Set<String> set = orTermsMap.keySet();
            for (String key : set) {
                TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(key, orTermsMap.get(key));
                boolQuery.should(termQueryBuilder);//OR
            }
        }
        String orMatchesStr = JSONObject.toJSONString(orMatches);
        Map<String, Object> orMatchesMap = JSON.parseObject(orMatchesStr);
        if (orMatchesMap != null) {
            Set<String> set = orMatchesMap.keySet();
            for (String key : set) {
                MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(key, orMatchesMap.get(key));//查询条件进行分词；eg:如果fieldValue是："read book",假设会被分词成read和book
                boolQuery.should(matchQueryBuilder);//OR
            }
        }
        String prefixItemsStr = JSONObject.toJSONString(prefixItems);
        Map<String, Object> prefixItemsMap = JSON.parseObject(prefixItemsStr);
        if (prefixItemsMap != null) {
            Set<String> set = prefixItemsMap.keySet();
            for (String key : set) {
                PrefixQueryBuilder prefixQueryBuilder = QueryBuilders.prefixQuery(key, (String) prefixItemsMap.get(key));//查询条件不进行分词
                sb.postFilter(prefixQueryBuilder);//前缀过滤（基于前面的查询结果）
            }
        }

        sb.from(page.getFrom());//分页查询-查询结果的开始位置：开始位置
        sb.size(page.getPageSize());//分页查询-返回的命中记录数：大小

        //多字段排序
        if (null != sortFields && sortFields.length>0) {
            for (int i = 0; i < sortFields.length; i++) {
                FieldSortBuilder fieldSort = SortBuilders.fieldSort(sortFields[i].getName());
                if (sortFields[i].getOrder() != null) {
                    fieldSort.order(sortFields[i].getOrder());
                } else {
                    fieldSort.order(SortOrder.DESC);
                }
                sb.sort(fieldSort);
            }
        }

        sb.query(boolQuery);

        searchRequest.source(sb);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();//命中的结果集
        EsPagination<T> esPagination = new EsPagination<>();
        if (null != hits) {
            List<T> responseList = new ArrayList<T>();
            for (SearchHit searchHit : searchResponse.getHits()) {
                Map<String, Object> sourceMap = searchHit.getSourceAsMap();
                sourceMap.put("esId", searchHit.getId());
                String sourceString = JSONObject.toJSONString(sourceMap);
                responseList.add(JSONObject.parseObject(sourceString, clazz));
            }
            esPagination.setData(responseList);
        }
        esPagination.setPage(page);
        return esPagination;
    }

    @Override
    public void process() {
        System.out.println("-----------------进行业务处理------------------");
    }

}
