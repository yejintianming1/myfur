package com.wu.myes.session;

import org.elasticsearch.action.search.SearchResponse;

import java.util.List;

public interface EsSession extends Cloneable{

    <T> SearchResponse search(T t) throws Exception;

    <T> List<T> searchT(T t) throws Exception;
}
