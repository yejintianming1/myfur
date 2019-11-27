package com.wu.fur.es.boot.client;

import java.util.Collection;

public class DefaultEsIndexAlgorithm implements EsIndexAlgorithm{
    @Override
    public String doSharding(Collection<String> collection, String shardingValue) {
        for (String item : collection) {
            int index = shardingValue.hashCode() % collection.size();
            if (item.endsWith(index+"")) {
                return item;
            }
        }
        return null;
    }
//    @Override
//    public String doIndex(EsSourceConfig config,String indexFieldValue) {
//        if (config.getIndexMode() == 0) {//单索引模式
//            return config.getIndexGroupNodes()[0];//取配置文件配置的索引
//        } else if (config.getIndexMode() == 1) {
//            //从索引节点中选取一个
////            return config.getIndexGroupName() + "_" +indexFieldValue.hashCode() % config.getIndexGroupNum();
//            for (int i = 0; i < config.getIndexGroupNodes().length; i++) {
//                int index = indexFieldValue.hashCode() % config.getIndexGroupNum();
//                if (config.getIndexGroupNodes()[i].endsWith(index+"")) {
//                    return config.getIndexGroupNodes()[i];
//                }
//            }
//        }
//        return null;
//    }
}
