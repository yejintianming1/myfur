package cn.wu.mock.mybatis.scripting;

import cn.wu.mock.mybatis.executor.parameter.ParameterHandler;
import cn.wu.mock.mybatis.mapping.BoundSql;
import cn.wu.mock.mybatis.mapping.MappedStatement;
import cn.wu.mock.mybatis.mapping.SqlSource;
import cn.wu.mock.mybatis.parsing.XNode;
import cn.wu.mock.mybatis.session.Configuration;

public interface LanguageDriver {

    ParameterHandler createParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql);

    SqlSource createSqlSource(Configuration configuration, XNode script, Class<?> parameterType);

    SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType);
}
