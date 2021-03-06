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
    public static String default_base_bean_name = "restEsClient";

    private ApplicationContext applicationContext;


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        Environment env = applicationContext.getEnvironment();

        //读取前缀为


        for (int i = 1; i <= 100; i++) {
            String hostPorts = env.getProperty("es.clientSource."+base_client+i+".host");
            if (StringUtils.isBlank(hostPorts)) break;
            String connectTimeout = env.getProperty("es.clientSource."+base_client+i+".connect-timeout");
            String socketTimeout = env.getProperty("es.clientSource."+base_client+i+".socket-timeout");
            String requestTimeout = env.getProperty("es.clientSource."+base_client+i+".request-timeout");
            String maxConnectNum = env.getProperty("es.clientSource."+base_client+i+".max-connect-num");
            String maxConnectRoute = env.getProperty("es.clientSource."+base_client+i+".max-connect-route");
            String indexGroupName = env.getProperty("es.clientSource."+base_client+i+".index-group-name");
            String indexGroupNum = env.getProperty("es.clientSource."+base_client+i+".index-group-num");
            String indexGroupNodes = env.getProperty("es.clientSource."+base_client+i+".index-group-nodes");
            String index = env.getProperty("es.clientSource."+base_client+i+".index");
            String type = env.getProperty("es.clientSource."+base_client+i+".type");
            String shards = env.getProperty("es.clientSource."+base_client+i+".shards");
            String replicas = env.getProperty("es.clientSource."+base_client+i+".replicas");
            String routingField = env.getProperty("es.clientSource."+base_client+i+".routing-field");
            String routingAlgorithmClassName = env.getProperty("es.clientSource."+base_client+i+".routing-algorithm-className");
            String indexField = env.getProperty("es.clientSource."+base_client+i+".index-field");
            String indexAlgorithmClassName = env.getProperty("es.clientSource."+base_client+i+".index-algorithm-className");
            String beanName = env.getProperty("es.clientSource."+base_client+i+".bean-name");

            if (StringUtils.isAnyBlank(hostPorts,type)) {//任意一个为空
                throw new RuntimeException("配置信息不完整");
            }
            if (StringUtils.isBlank(indexGroupName) && StringUtils.isBlank(index)) {//如果提供了此属性，则认为采用索引组模式
                throw new RuntimeException("配置信息不完整");
            }
            if (!StringUtils.isBlank(indexGroupName) && StringUtils.isBlank(indexGroupNum)) {
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
            if (!StringUtils.isBlank(indexGroupName)) {
                esSourceConfig.setIndexGroupName(indexGroupName);
                esSourceConfig.setIndexMode(1);
                esSourceConfig.setIndexGroupNum(Integer.valueOf(indexGroupNum));
                if (!StringUtils.isBlank(indexGroupNodes)) {
                    String[] indexGroupNodeArr = indexGroupNodes.split(",");
                    esSourceConfig.setIndexGroupNodes(indexGroupNodeArr);
                }
            } else if (!StringUtils.isBlank(index)) {
                esSourceConfig.setIndexGroupNodes(new String[]{ index});
                esSourceConfig.setIndexMode(0);
            }
            if (!StringUtils.isBlank(type)) esSourceConfig.setType(type);
            if (!StringUtils.isBlank(shards)) esSourceConfig.setShards(Integer.valueOf(shards));
            if (!StringUtils.isBlank(replicas)) esSourceConfig.setReplicas(Integer.valueOf(replicas));
            //路由算法
            if (!StringUtils.isBlank(routingField)) esSourceConfig.setRoutingField(routingField);
            if (!StringUtils.isBlank(routingAlgorithmClassName)) {
                Class<?> result = null;
                try {
                    result = Class.forName(routingAlgorithmClassName);
                    if (!EsRoutingAlgorithm.class.isAssignableFrom(result)) {
                        throw new RuntimeException(String.format("Class %s should be implement %s", routingAlgorithmClassName, EsRoutingAlgorithm.class.getName()));
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    esSourceConfig.setEsRoutingAlgorithm((EsRoutingAlgorithm)result.newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            //索引算法
            if (!StringUtils.isBlank(indexField)) esSourceConfig.setIndexField(indexField);
            if (!StringUtils.isBlank(indexAlgorithmClassName)) {
                Class<?> result = null;
                try {
                    result = Class.forName(indexAlgorithmClassName);
                    if (!EsIndexAlgorithm.class.isAssignableFrom(result)) {
                        throw new RuntimeException(String.format("Class %s should be implement %s", indexAlgorithmClassName, EsIndexAlgorithm.class.getName()));
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    esSourceConfig.setEsIndexAlgorithm((EsIndexAlgorithm)result.newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            if (StringUtils.isBlank(beanName)) beanName = default_base_bean_name+i;

            BeanDefinition beanDe = BeanDefinitionBuilder.rootBeanDefinition(RestEsClient.class)
                    .addPropertyValue("esSourceConfig",esSourceConfig)
                    .setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_NAME)
                    .getBeanDefinition();
            registry.registerBeanDefinition(beanName,beanDe);

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
