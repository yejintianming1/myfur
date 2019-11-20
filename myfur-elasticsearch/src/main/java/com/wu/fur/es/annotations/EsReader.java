package com.wu.fur.es.annotations;

import java.lang.reflect.Field;

public class EsReader {

    /**
     * 读取mappings
     * @param clazz
     * @return
     */
    public static String readerMappingJson(Class clazz) {
        StringBuilder builder = new StringBuilder("{\"properties\":{");//"{\"properties\":{\"message\":{\"type\":\"text\"}}}"

//        Class<?> clazz = t.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field: fields
             ) {
            String type = "keyword";
            if (field.isAnnotationPresent(EsFieldType.class)) {
                EsFieldType esFieldType = field.getAnnotation(EsFieldType.class);
                type = esFieldType.value();
            }
            String name = field.getName();
            builder.append("\""+name+"\":{\"type\":\""+type+"\"},");
        }
        String str = builder.toString();
        if (str.endsWith(",")) {
            str = str.substring(0,str.length()-1);
        }
        return str + "}}";
    }

    public static <T> String readRouting(T t) throws Exception {
        String routing = null;
        Class<?> clazz = t.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field: fields
             ) {
            if (field.isAnnotationPresent(EsRouting.class)) {
                field.setAccessible(true);
                //说明根据此字段值进行路由
                routing = field.get(t).toString();
            }
        }
        return routing;
    }


}
