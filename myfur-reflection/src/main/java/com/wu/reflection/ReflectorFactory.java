package com.wu.reflection;

/**反射器工厂*/
public interface ReflectorFactory {

    boolean isClassCacheEnabled();

    void setClassCacheEnabled(boolean classCacheEnabled);

    /**寻找某个Class的反射器*/
    Reflector findForClass(Class<?> type);
}
