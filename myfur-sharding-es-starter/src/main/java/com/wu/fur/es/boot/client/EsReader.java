package com.wu.fur.es.boot.client;

import org.elasticsearch.client.RestHighLevelClient;

import java.lang.reflect.Field;
import java.util.Map;

public class EsReader {

    public static <T> String readFieldValue(T t,String fieldName) throws Exception {
        String value = null;
        Class<?> clazz = t.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field: fields
        ) {
            if (fieldName.equals(field.getName())) {
                field.setAccessible(true);
                Object o = field.get(t);
                if (o != null) {
                    value = o.toString();
                }
                break;
            }
        }
        return value;
    }

    public static <T> String readLogicIndexName(T t) throws Exception {
        Class<?> clazz = t.getClass();
        if (clazz.isAnnotationPresent(EsLogicIndex.class)) {
            return clazz.getAnnotation(EsLogicIndex.class).value();
        }
        return null;
    }

    public static <T> RestHighLevelClient readClient(T t, EsSourceConfig esSourceConfig) throws Exception  {
        //数据源应该从逻辑表获取索引组
        String logicIndexName = EsReader.readLogicIndexName(t);//获取到逻辑索引名
        //读取配置文件配置的逻辑索引名和数据源之间的对应关系，找出该逻辑索引名对应的数据源[组]
        Map<String, String> logicIndexMap = esSourceConfig.getLogicIndexMap();
        String sourceName = logicIndexMap.get(logicIndexName);
        Map<String, EsRestDataSource> sourceMap = esSourceConfig.getSourceMap();//维护了数据源名和数据源对象的map
        EsRestDataSource esRestDataSource = sourceMap.get(sourceName);//获取到数据源对象
        return esRestDataSource.getClient();
    }


}
