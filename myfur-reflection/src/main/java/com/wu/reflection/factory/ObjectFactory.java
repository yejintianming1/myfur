package com.wu.reflection.factory;

import java.util.List;
import java.util.Properties;

/**
 * 对象工厂
 */
public interface ObjectFactory {

    /** require JDK8+ */
    default void setProperties(Properties properties) {
        //默认啥也不做
    }

    /** 根据Class创建创建对象 */
    <T> T create(Class<T> type);

    /** 指定Class和构造函数创建对象 */
    <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs);

    /** 判断是否是集合 */
    <T> boolean isCollection(Class<T> type);
}
