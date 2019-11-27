package com.wu.fur.es.boot.client;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;

public class RestEsClientRegistry implements EsClientRegistry, BeanDefinitionRegistryPostProcessor {

    public Map<String, Object> esConfigMap;//es的配置对象

    private EsApi esApi = new RestEsApi();

    @PostConstruct
    public void init() {
        //进行初始化操作
        //读取配置
        System.out.println("-------------init---------------");
    }

    @PreDestroy
    public void close() {
        System.out.println("-------------close---------------");
    }

    @Override
    public void say() {
        System.out.println("------------------------------------");
        System.out.println("Hello RestEsClientRegistry");
        System.out.println("------------------------------------");
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        //此处进行EsClient注册和属性注入

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
