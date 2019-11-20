package com.wu.fur.es.client;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RestEsClientRegistry implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {


    public static String base_client = "client";
    public static String base_bean_name = "restEsClient";

    private ApplicationContext applicationContext;


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        Environment env = applicationContext.getEnvironment();

        for (int i = 1; i <= 100; i++) {
            String hostPorts = env.getProperty("es.clientSource."+base_client+i+".host");
            if (StringUtils.isBlank(hostPorts)) break;
            String connectTimeout = env.getProperty("es.clientSource."+base_client+i+".connect-timeout");
            String socketTimeout = env.getProperty("es.clientSource."+base_client+i+".socket-timeout");
            String requestTimeout = env.getProperty("es.clientSource."+base_client+i+".request-timeout");
            String maxConnectNum = env.getProperty("es.clientSource."+base_client+i+".max-connect-num");
            String maxConnectRoute = env.getProperty("es.clientSource."+base_client+i+".max-connect-route");
            String index = env.getProperty("es.clientSource."+base_client+i+".index");
            String type = env.getProperty("es.clientSource."+base_client+i+".type");
            String shards = env.getProperty("es.clientSource."+base_client+i+".shards");
            String replicas = env.getProperty("es.clientSource."+base_client+i+".replicas");

            if (StringUtils.isAnyBlank(hostPorts,index,type)) {//任意一个为空
                throw new RuntimeException("配置信息不完整");
            }

            //hostPort处理
            String[] hostPort = hostPorts.split(",");
            List<HttpHost> hostList = new ArrayList<>();
            for (int j = 0; j < hostPort.length; j++) {
                String[] hp = hostPort[j].split( ":");
                String host = hp[0];
                String port = hp[1];
                //构建HttpPort对象
                HttpHost httpHost = new HttpHost(host,Integer.valueOf(port));
                hostList.add(httpHost);
            }
            //进行ES数据源配置对象的封装
            EsSourceConfig esSourceConfig = new EsSourceConfig();
            esSourceConfig.setHttpHosts(hostList.toArray(new HttpHost[hostList.size()]));
            if (!StringUtils.isBlank(connectTimeout)) esSourceConfig.setConnectTimeout(Integer.valueOf(connectTimeout));
            if (!StringUtils.isBlank(socketTimeout)) esSourceConfig.setSocketTimeout(Integer.valueOf(socketTimeout));
            if (!StringUtils.isBlank(requestTimeout)) esSourceConfig.setRequestTimeout(Integer.valueOf(requestTimeout));
            if (!StringUtils.isBlank(maxConnectNum)) esSourceConfig.setMaxConnectNum(Integer.valueOf(maxConnectNum));
            if (!StringUtils.isBlank(maxConnectRoute)) esSourceConfig.setMaxConnectRoute(Integer.valueOf(maxConnectRoute));
            esSourceConfig.setIndex(index);
            if (!StringUtils.isBlank(type)) esSourceConfig.setType(type);
            if (!StringUtils.isBlank(shards)) esSourceConfig.setShards(Integer.valueOf(shards));
            if (!StringUtils.isBlank(replicas)) esSourceConfig.setReplicas(Integer.valueOf(replicas));

            BeanDefinition beanDe = BeanDefinitionBuilder.rootBeanDefinition(RestEsClient.class)
                    .addPropertyValue("esSourceConfig",esSourceConfig)
                    .setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_NAME)
                    .getBeanDefinition();
            registry.registerBeanDefinition(base_bean_name+i,beanDe);

        }

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


}
