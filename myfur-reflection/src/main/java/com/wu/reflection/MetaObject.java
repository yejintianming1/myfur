package com.wu.reflection;

import com.wu.reflection.factory.ObjectFactory;
import com.wu.reflection.property.PropertyTokenizer;
import com.wu.reflection.wrapper.*;

import java.util.Collection;
import java.util.List;
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

    /** 获取元数据对象 */
    public static MetaObject forObject(Object object, ObjectFactory objectFactory, ObjectWrapperFactory objectWrapperFactory, ReflectorFactory reflectorFactory) {
        if (object == null) {
            return SystemMetaObject.NULL_META_OBJECT;
        } else {
            return new MetaObject(object, objectFactory, objectWrapperFactory, reflectorFactory);
        }
    }

    public ObjectFactory getObjectFactory() {
        return objectFactory;
    }

    public ObjectWrapperFactory getObjectWrapperFactory() {
        return objectWrapperFactory;
    }

    public ReflectorFactory getReflectorFactory() {
        return reflectorFactory;
    }

    public Object getOriginalObject() {
        return originalObject;
    }

    /** 查找属性 */
    public String findProperty(String propName, boolean useCamelCaseMapping) {
        //委托给objectWrapper查找属性
        return objectWrapper.findProperty(propName, useCamelCaseMapping);
    }

    public String[] getGetterNames() {
        //委托给objectWrapper获取Getter names
        return objectWrapper.getGetterNames();
    }

    public String[] getSetterNames() {
        //委托给objectWrapper获取Setter names
        return objectWrapper.getSetterNames();
    }

    public Class<?> getSetterType(String name) {
        //委托给objectWrapper获取Setter type
        return objectWrapper.getSetterType(name);
    }

    public Class<?> getGetterType(String name) {
        //委托给objectWrapper获取Getter type
        return objectWrapper.getGetterType(name);
    }

    public boolean hasSetter(String name) {
        //委托给objectWrapper判断是否有setter
        return objectWrapper.hasSetter(name);
    }

    public boolean hasGetter(String name) {
        //委托给objectWrapper判断是否有getter
        return objectWrapper.hasGetter(name);
    }

    /** 根据属性名获取值*/
    public Object getValue(String name) {
        PropertyTokenizer prop = new PropertyTokenizer(name);
        if (prop.hasNext()) {
            MetaObject metaValue = metaObjectForProperty(prop.getIndexedName());
            if (metaValue == SystemMetaObject.NULL_META_OBJECT) {
                return null;
            } else {
                return metaValue.getValue(prop.getChildren());
            }
        } else {
            return objectWrapper.get(prop);//最终也是委托objectWrapper获取name的属性值
        }
    }

    public void setValue(String name, Object value) {
        PropertyTokenizer prop = new PropertyTokenizer(name);
        if (prop.hasNext()) {
            MetaObject metaValue = metaObjectForProperty(prop.getIndexedName());
            if (metaValue == SystemMetaObject.NULL_META_OBJECT) {
                if (value == null) {
                    // don't instantiate child path if value is null
                    return;
                } else {
                    metaValue = objectWrapper.instantiatePropertyValue(name, prop, objectFactory);
                }
            }
            metaValue.setValue(prop.getChildren(), value);
        } else {
            objectWrapper.set(prop, value);
        }
    }

    /** 根据属性名获取属性值对应的元数据对象*/
    public MetaObject metaObjectForProperty(String name) {
        Object value = getValue(name);
        return MetaObject.forObject(value, objectFactory, objectWrapperFactory, reflectorFactory);
    }

    public ObjectWrapper getObjectWrapper() {
        return objectWrapper;
    }

    public boolean isCollection() {
        return objectWrapper.isCollection();
    }

    public void add(Object element) {
        objectWrapper.add(element);
    }

    public <E> void addAll(List<E> list) {
        objectWrapper.addAll(list);
    }




}
