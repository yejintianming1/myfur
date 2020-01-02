package cn.wu.mock.mybatis.session;

import cn.wu.mock.mybatis.cache.decorators.FifoCache;
import cn.wu.mock.mybatis.cache.decorators.LruCache;
import cn.wu.mock.mybatis.cache.decorators.SoftCache;
import cn.wu.mock.mybatis.cache.decorators.WeakCache;
import cn.wu.mock.mybatis.cache.impl.PerpetualCache;
import cn.wu.mock.mybatis.datasource.jndi.JndiDataSourceFactory;
import cn.wu.mock.mybatis.datasource.pooled.PooledDataSourceFactory;
import cn.wu.mock.mybatis.datasource.unpooled.UnpooledDataSourceFactory;
import cn.wu.mock.mybatis.mapping.VendorDatabaseIdProvider;
import cn.wu.mock.mybatis.reflection.MetaObject;
import cn.wu.mock.mybatis.scripting.xmltags.XMLLanguageDriver;
import cn.wu.mock.mybatis.transaction.jdbc.JdbcTransactionFactory;
import cn.wu.mock.mybatis.transaction.managed.ManagedTransactionFactory;
import cn.wu.mock.mybatis.type.TypeAliasRegistry;
import cn.wu.mock.mybatis.type.TypeHandlerRegistry;

import java.util.Properties;

/**
 * 核心的配置类
 */
public class Configuration {


    protected boolean lazyLoadingEnabled = false;

    protected final TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();
    protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();

    //保存变量
    protected Properties variables = new Properties();

    public Configuration() {
        typeAliasRegistry.registerAlias("JDBC", JdbcTransactionFactory.class);//jdbc transaction
        typeAliasRegistry.registerAlias("MANAGED", ManagedTransactionFactory.class);

        typeAliasRegistry.registerAlias("JNDI", JndiDataSourceFactory.class);//由容器管理的通过JNDI方式注入的数据源
        typeAliasRegistry.registerAlias("POOLED", PooledDataSourceFactory.class);//带连接池的数据源
        typeAliasRegistry.registerAlias("UNPOOLED", UnpooledDataSourceFactory.class);//不带连接池的数据源

        typeAliasRegistry.registerAlias("PERPETUAL", PerpetualCache.class);
        typeAliasRegistry.registerAlias("FIFO", FifoCache.class);
        typeAliasRegistry.registerAlias("LRU", LruCache.class);
        typeAliasRegistry.registerAlias("SOFT", SoftCache.class);
        typeAliasRegistry.registerAlias("WEAK", WeakCache.class);

        typeAliasRegistry.registerAlias("DB_VENDOR", VendorDatabaseIdProvider.class);

        typeAliasRegistry.registerAlias("XML", XMLLanguageDriver.class);
    }

    public boolean isLazyLoadingEnabled() {
        return lazyLoadingEnabled;
    }

    public Properties getVariables() {
        return variables;
    }

    public MetaObject newMetaObject(Object object) {
        return null;
//        return MetaObject.forObject(object, objectFactory, objectWrapperFactory, reflectorFactory);
    }

    public TypeHandlerRegistry getTypeHandlerRegistry() {
        return typeHandlerRegistry;
    }

    public TypeAliasRegistry getTypeAliasRegistry() {
        return typeAliasRegistry;
    }

    public void setVariables(Properties variables) {
        this.variables = variables;
    }
}
