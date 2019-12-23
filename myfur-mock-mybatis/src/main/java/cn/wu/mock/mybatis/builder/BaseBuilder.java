package cn.wu.mock.mybatis.builder;

import cn.wu.mock.mybatis.session.Configuration;
import cn.wu.mock.mybatis.type.TypeAliasRegistry;
import cn.wu.mock.mybatis.type.TypeHandlerRegistry;

public abstract class BaseBuilder {

    protected final Configuration configuration;
    protected final TypeAliasRegistry typeAliasRegistry;
    protected final TypeHandlerRegistry typeHandlerRegistry;

    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
        this.typeAliasRegistry = this.configuration.getTypeAliasRegistry();
        this.typeHandlerRegistry = this.configuration.getTypeHandlerRegistry();
    }


}
