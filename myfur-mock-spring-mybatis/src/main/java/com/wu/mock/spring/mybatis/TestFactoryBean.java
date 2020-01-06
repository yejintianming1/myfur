package com.wu.mock.spring.mybatis;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class TestFactoryBean implements ImportBeanDefinitionRegistrar,BeanDefinitionRegistryPostProcessor, InitializingBean, ApplicationContextAware, BeanNameAware, FactoryBean, ApplicationListener<ApplicationEvent>, DisposableBean {
    @Override
    public void setBeanName(String name) {
        //BeanNameAware
        System.out.println("BeanNameAware.setBeanName(name)");
    }

    @Override
    public Object getObject() throws Exception {
        System.out.println("FactoryBean.getObject()");
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        System.out.println("FactoryBean.getObjectType");
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean.afterPropertiesSet()");
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("BeanDefinitionRegistryPostProcessor.postProcessBeanDefinitionRegistry(registry)");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactoryPostProcessor.postProcessBeanFactory(beanFactory)");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("ApplicationContextAware.setApplicationContext(applicationContext)");
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("ApplicationListener.onApplicationEvent(event)");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean.destroy");
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        System.out.println("ImportBeanDefinitionRegistrar.registerBeanDefinitions(importingClassMetadata,registry)");
    }
}
