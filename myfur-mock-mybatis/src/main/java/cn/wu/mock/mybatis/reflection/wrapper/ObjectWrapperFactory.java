package cn.wu.mock.mybatis.reflection.wrapper;

import cn.wu.mock.mybatis.reflection.MetaObject;

public interface ObjectWrapperFactory {

    boolean hasWrapperFor(Object object);

    ObjectWrapper getWrapperFor(MetaObject metaObject, Object object);
}
