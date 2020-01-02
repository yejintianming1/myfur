package com.wu.reflection.factory;

import com.wu.reflection.ReflectionException;
import com.wu.reflection.Reflector;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.ReflectPermission;
import java.util.*;
import java.util.stream.Collectors;

public class DefaultObjectFactory implements ObjectFactory, Serializable {
    private static final long serialVersionUID = 7123529959076761493L;

    @Override
    public <T> T create(Class<T> type) {
        return create(type, null, null);
    }

    @Override
    public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
        Class<?> classToCreate = resolveInterface(type);//获取需要实例化的Class
        return (T) instantiateClass(classToCreate, constructorArgTypes, constructorArgs);
    }

    /** 根据类型和构造函数创建对象 */
    private <T> T instantiateClass(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
        Constructor<T> constructor;
        try {
            //无参构造
            if (constructorArgTypes == null || constructorArgs == null) {
                constructor = type.getDeclaredConstructor();
                try {
                    return constructor.newInstance();
                } catch (IllegalAccessException e) {
                    if (Reflector.canControlMemberAccessible()) {
                        constructor.setAccessible(true);
                        return constructor.newInstance();
                    } else {
                        throw e;
                    }
                }
            }
            //有参构造
            constructor = type.getDeclaredConstructor(constructorArgTypes.toArray(new Class[constructorArgTypes.size()]));
            try {
                return constructor.newInstance(constructorArgs.toArray(new Object[constructorArgs.size()]));
            } catch (IllegalAccessException e) {
                if (Reflector.canControlMemberAccessible()) {
                    constructor.setAccessible(true);
                    return constructor.newInstance(constructorArgs.toArray(new Object[constructorArgs.size()]));
                } else {
                    throw e;
                }
            }
        } catch (Exception e) {
            String argTypes = Optional.ofNullable(constructorArgTypes).orElseGet(Collections::emptyList)
                    .stream().map(Class::getSimpleName).collect(Collectors.joining(","));
            String argValues = Optional.ofNullable(constructorArgs).orElseGet(Collections::emptyList)
                    .stream().map(String::valueOf).collect(Collectors.joining(","));
            throw new ReflectionException("Error instantiating " + type + " with invalid types (" + argTypes + ") or values (" + argValues + "). Cause: " + e, e);
        }
    }

    @Override
    public <T> boolean isCollection(Class<T> type) {
        return Collection.class.isAssignableFrom(type);
    }


    protected <T> Class<?> resolveInterface(Class<T> type) {
        Class<?> classToCreate;
        //将集合接口转为兼容的具体类型，因为创建对象时需要具体对象
        if (type == List.class || type == Collection.class || type == Iterable.class) {
            classToCreate = ArrayList.class;
        } else if (type == Map.class) {
            classToCreate = HashMap.class;
        } else if (type == SortedSet.class) {
            classToCreate = TreeSet.class;
        } else if (type == Set.class) {
            classToCreate = HashSet.class;
        } else {
            classToCreate = type;
        }
        return classToCreate;
    }

}
