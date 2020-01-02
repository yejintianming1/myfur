package com.wu.reflection.invoker;

import com.wu.reflection.Reflector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 方法调用者
 */
public class MethodInvoker implements Invoker {

    private final Class<?> type;
    private final Method method;

    public MethodInvoker(Method method) {
        this.method = method;

        if (method.getParameterTypes().length == 1) {//当这个方法只有一个参数时，返回该类型；否则返回返回值类型
            type = method.getParameterTypes()[0];
        } else {
            type = method.getReturnType();
        }
    }

    @Override
    public Object invoke(Object target, Object[] args) throws IllegalAccessException, InvocationTargetException {
        try {
            return method.invoke(target, args);
        } catch (IllegalAccessException e) {
           if (Reflector.canControlMemberAccessible()) {
               method.setAccessible(true);
               return method.invoke(target, args);
           } else {
               throw e;
           }
        }
    }

    @Override
    public Class<?> getType() {
        return type;
    }
}
