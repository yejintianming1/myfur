package com.wu.reflection.wrapper;

import com.wu.reflection.MetaObject;

/**
 * 包装对象工厂
 */
public interface ObjectWrapperFactory {

    /**是否有包装*/
    boolean hasWrapperFor(Object object);

    /**获取包装对象*/
    ObjectWrapper getWrapperFor(MetaObject metaObject, Object object);
}
