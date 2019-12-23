package com.wu.fur.es.boot.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtils implements ApplicationContextAware {

    static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.context = context;
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static void autowireBean(Object bean) {
        context.getAutowireCapableBeanFactory().autowireBean(bean);
    }

    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }
}
