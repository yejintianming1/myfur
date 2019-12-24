package com.wu.reflection;

import com.wu.reflection.factory.DefaultObjectFactory;
import com.wu.reflection.factory.ObjectFactory;

public class SystemMetaObject {

    //对象工厂
    public static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    public static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();

    private SystemMetaObject() {
        //私有化构造函数
    }

    private static class NullObject {
        //静态内部类
    }

}
