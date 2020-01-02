package com.wu.reflection;

import com.wu.reflection.factory.DefaultObjectFactory;
import com.wu.reflection.factory.ObjectFactory;
import com.wu.reflection.wrapper.DefaultObjectWrapperFactory;
import com.wu.reflection.wrapper.ObjectWrapperFactory;

public class SystemMetaObject {

    //对象工厂
    public static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    //包装对象工厂
    public static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    //空元数据对象
    public static final MetaObject NULL_META_OBJECT = MetaObject.forObject(NullObject.class, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());

    private SystemMetaObject() {
        //私有化构造函数
    }

    private static class NullObject {
        //静态内部类
    }

    public static MetaObject forObject(Object object) {
        return MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
    }

}
