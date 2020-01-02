package com.wu.reflection.wrapper;

import com.wu.reflection.MetaObject;
import com.wu.reflection.ReflectionException;

public class DefaultObjectWrapperFactory implements ObjectWrapperFactory {
    @Override
    public boolean hasWrapperFor(Object object) {
        //默认的直接返回false
        return false;
    }

    @Override
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        throw new ReflectionException("默认的包装对象工厂不会提供一个包装对象");
    }
}
