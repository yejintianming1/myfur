package com.wu.fur.es.boot;


import com.wu.fur.es.boot.client.*;
import com.wu.fur.es.boot.sharding.ShardingRuleConfigurationProperties;
import com.wu.fur.es.boot.sharding.ShardingTableRuleConfiguration;
import com.wu.fur.es.boot.utils.EsDataSourceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
@EnableConfigurationProperties({ShardingRuleConfigurationProperties.class})
@ConditionalOnProperty(prefix = "spring.shardingses", name = "enabled", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
public class SpringBootConfiguration implements EnvironmentAware,DisposableBean {

    private final ShardingRuleConfigurationProperties shardingProperties;

    //数据源:key=数据源名称，数据源
    private final Map<String, EsDataSource> dataSourceMap = new LinkedHashMap<>();

    //默认数据源
    private EsDataSource defaultDataSource;

    //分片规则:key=逻辑索引，value=数据源名称
    private final Map<String, EsShadingTableRule> ruleMap = new LinkedHashMap<>();

    //逻辑
    private final Map<String, EsRestDataSource> logicDataSourceMap = new LinkedHashMap<>();

    private EsContext esContext;


    @Bean
    public EsContext esContext() {
        esContext = new EsShardingContext(dataSourceMap,defaultDataSource,ruleMap);
        return esContext;
    }


    @Override
    public final void setEnvironment(final Environment environment) {

        //数据源的封装
        String prefix = "spring.shardingses.datasource.";
        String[] dataSourceNames = environment.getProperty(prefix + "names").split(",");
        for (String each : dataSourceNames) {
            Binder binder = Binder.get(environment);//获取binder对象
            Map<String,Object> dataSourceProps = binder.bind(prefix+each, Map.class).get();
            dataSourceMap.put(each,EsDataSourceUtil.builder(dataSourceProps));
        }

        //默认数据源的封装
        String defaultDataSourceName = environment.getProperty("spring.shardingses.sharding.default-data-source-name");
        defaultDataSource = dataSourceMap.get(defaultDataSourceName);

        //分片规则实体封装
        Map<String, ShardingTableRuleConfiguration> tables = shardingProperties.getTables();
        //获取到属性值
        for (Map.Entry<String,ShardingTableRuleConfiguration> item: tables.entrySet()) {
            //map
            EsShadingTableRule tableRule = new EsShadingTableRule();
            tableRule.setActualDataNode(item.getValue().getActualDataNode());
            EsIndexAlgorithm esIndexAlgorithm = null;
            try {
                esIndexAlgorithm = (EsIndexAlgorithm) Class.forName(item.getValue().getIndexAlgorithmClassName()).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            tableRule.setIndexColumn(item.getValue().getIndexColumn());
            tableRule.setEsIndexAlgorithm(esIndexAlgorithm);
            tableRule.setRoutingColumn(item.getValue().getRoutingColumn());
            EsRoutingAlgorithm esRoutingAlgorithm = null;
            try {
                esRoutingAlgorithm = (EsRoutingAlgorithm) Class.forName(item.getValue().getRoutingAlgorithmClassName()).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            tableRule.setEsRoutingAlgorithm(esRoutingAlgorithm);
            ruleMap.put(item.getKey(),tableRule);
        }

        //

    }

    @Override
    public void destroy() throws Exception {
        System.out.println("-------------------close--------------");
        esContext.close();
    }
}
