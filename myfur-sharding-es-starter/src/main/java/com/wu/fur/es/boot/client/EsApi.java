package com.wu.fur.es.boot.client;

/**
 * 对外使用的Api
 */
public interface EsApi {


    <T> boolean indexDoc(T t) throws Exception;

    <T,Q,E,O,R,W> EsPagination<T> search(Q terms,E matches,O orTerms,R orMatches,W prefixItems,SortField[] sortFields,EsPage page,Class<T> clazz) throws Exception;


    //此处为测试方法
    void process();
}
