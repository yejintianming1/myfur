package com.wu.reflection.test.entity;

import com.wu.reflection.DefaultReflectorFactory;
import com.wu.reflection.MetaObject;
import com.wu.reflection.factory.DefaultObjectFactory;
import com.wu.reflection.wrapper.DefaultObjectWrapperFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * 用户
 */
public class User implements Say {

    private String name;
    private int age;
    private String sex;
    private boolean isRich;

    public User() {
    }

    public User(String name, int age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isRich() {
        return isRich;
    }

    public void setRich(boolean rich) {
        isRich = rich;
    }

//    @Override
//    public void say(Properties properties) {
//        MetaObject metaObject = MetaObject.forObject(this, new DefaultObjectFactory(), new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());
//        for (Object key : properties.keySet()) {
//            String propertyName = (String) key;
//            if (metaObject.hasSetter(propertyName)) {
//                String value = properties.getProperty(propertyName);
//                Object convertedValue = value;
////                Class<?> targetType = metaObject.getSetterType(propertyName);
////                if (targetType == Integer.class || targetType == int.class) {
////                    convertedValue = Integer.valueOf(value);
////                } else if (targetType == Long.class || targetType == long.class) {
////                    convertedValue = Long.valueOf(value);
////                } else if (targetType == Boolean.class || targetType == boolean.class) {
////                    convertedValue = Boolean.valueOf(value);
////                }
//                metaObject.setValue(propertyName, convertedValue);
//            }
//        }
//    }


    @Override
    public void say(Properties properties) {
        MetaObject metaObject = MetaObject.forObject(this, new DefaultObjectFactory(), new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());
        Iterator<Map.Entry<Object, Object>> it = properties.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Object, Object> next = it.next();
            String propertyName = next.getKey() != null ? next.getKey().toString() : null;
            if (metaObject.hasSetter(propertyName)) {
                metaObject.setValue(propertyName, next.getValue());
            }
        }
    }

    private Object convertValue(MetaObject metaObject, String propertyName, String value) {
        Object convertedValue = value;
        Class<?> targetType = metaObject.getSetterType(propertyName);
        if (targetType == Integer.class || targetType == int.class) {
            convertedValue = Integer.valueOf(value);
        } else if (targetType == Long.class || targetType == long.class) {
            convertedValue = Long.valueOf(value);
        } else if (targetType == Boolean.class || targetType == boolean.class) {
            convertedValue = Boolean.valueOf(value);
        }
        return convertedValue;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", isRich=" + isRich +
                '}';
    }
}
