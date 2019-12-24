package com.wu.reflection.wrapper;

import com.wu.reflection.MetaObject;

/**
 * 包装对象工厂
 */
public interface ObjectWrapperFactory {

    boolean hasWrapperFor(Object object);

    ObjectWrapper getWrapperFor(MetaObject metaObject, Object object);
}
