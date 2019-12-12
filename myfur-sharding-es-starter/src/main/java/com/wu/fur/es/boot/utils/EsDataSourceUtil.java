package com.wu.fur.es.boot.utils;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Sets;
import com.wu.fur.es.boot.client.EsDataSource;
import com.wu.fur.es.boot.client.EsRestDataSource;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.lang.reflect.Method;
import java.util.*;

public class EsDataSourceUtil {

    private static final String SET_METHOD_PREFIX = "set";

    private static Collection<Class<?>> generalClassType;

    static {
        generalClassType = Sets.<Class<?>>newHashSet(boolean.class, Boolean.class, int.class, Integer.class, long.class, Long.class, String.class);
    }

    public static EsDataSource builder(Map<String, Object> dataSourceProps) {
        EsDataSource esDataSource = new EsRestDataSource();
        dataSourceProps.forEach((key,value) -> {
            callSetterMethod(esDataSource,getSetterMethodName(key), null == value ? null : value.toString());
        });
        if (esDataSource.getHttpHosts() == null) {
            esDataSource.buildHttpHosts();
        }
        //实际索引节点
        if (esDataSource.getActualIndexNodes() == null) {
            esDataSource.buildActualIndexNodes();
        }
        //client
        if (esDataSource.getClient() == null) {
            esDataSource.buildClient();
        }
        return esDataSource;
    }



    private static String getSetterMethodName(final String propertyName) {
        if (propertyName.contains("-")) {
            return CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, SET_METHOD_PREFIX + "-" + propertyName);
        }
        return SET_METHOD_PREFIX + String.valueOf(propertyName.charAt(0)).toUpperCase() + propertyName.substring(1, propertyName.length());
    }

    private static void callSetterMethod(final EsDataSource dataSource, final String methodName, final String setterValue) {
        for (Class<?> each : generalClassType) {
            try {
                Method method = dataSource.getClass().getMethod(methodName, each);
                if (boolean.class == each || Boolean.class == each) {
                    method.invoke(dataSource, Boolean.valueOf(setterValue));
                } else if (int.class == each || Integer.class == each) {
                    method.invoke(dataSource, Integer.parseInt(setterValue));
                } else if (long.class == each || Long.class == each) {
                    method.invoke(dataSource, Long.parseLong(setterValue));
                } else {
                    method.invoke(dataSource, setterValue);
                }
                return;
            } catch (final ReflectiveOperationException ignored) {
            }
        }
    }
}
