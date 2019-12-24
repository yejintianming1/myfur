package com.wu.reflection;

import com.wu.reflection.factory.ObjectFactory;
import com.wu.reflection.wrapper.ObjectWrapper;
import com.wu.reflection.wrapper.ObjectWrapperFactory;

import java.util.Collection;
import java.util.Map;

/**
 * 对象元数据
 */
public class MetaObject {

    private final Object originalObject;//原始对象
    private final ObjectWrapper objectWrapper;//包装对象
    private final ObjectFactory objectFactory;//对象工厂
    private final ObjectWrapperFactory objectWrapperFactory;//包装对象工厂
    private final ReflectorFactory reflectorFactory;//反射器工厂

    private MetaObject(Object object, ObjectFactory objectFactory, ObjectWrapperFactory objectWrapperFactory, ReflectorFactory reflectorFactory) {
        this.originalObject = object;
        this.objectFactory = objectFactory;
        this.objectWrapperFactory = objectWrapperFactory;
        this.reflectorFactory = reflectorFactory;

        if (object instanceof ObjectWrapper) {//如果是包装对象，直接强转成包装对象
            this.objectWrapper = (ObjectWrapper) object;
        } else if (objectWrapperFactory.hasWrapperFor(object)) {//如果存在包装对象
            this.objectWrapper = objectWrapperFactory.getWrapperFor(this, object);//获取包装对象
        } else if (object instanceof Map) {//如果是Map
          this.objectWrapper = new MapWrapper(this, (Map) object);
        } else if (object instanceof Collection) {//如果是一个集合
            this.objectWrapper = new CollectionWrapper(this, (Collection) object);
        } else {//否则当做Bean处理
            this.objectWrapper = new BeanWrapper(this, object);
        }
    }
}
