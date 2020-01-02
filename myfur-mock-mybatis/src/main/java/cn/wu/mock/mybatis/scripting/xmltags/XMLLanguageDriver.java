package cn.wu.mock.mybatis.scripting.xmltags;

import cn.wu.mock.mybatis.builder.xml.XMLMapperEntityResolver;
import cn.wu.mock.mybatis.executor.parameter.ParameterHandler;
import cn.wu.mock.mybatis.mapping.BoundSql;
import cn.wu.mock.mybatis.mapping.MappedStatement;
import cn.wu.mock.mybatis.mapping.SqlSource;
import cn.wu.mock.mybatis.parsing.XNode;
import cn.wu.mock.mybatis.parsing.XPathParser;
import cn.wu.mock.mybatis.scripting.LanguageDriver;
import cn.wu.mock.mybatis.scripting.defaults.DefaultParameterHandler;
import cn.wu.mock.mybatis.session.Configuration;

public class XMLLanguageDriver implements LanguageDriver {
    @Override
    public ParameterHandler createParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
        return new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
    }

    @Override
    public SqlSource createSqlSource(Configuration configuration, XNode script, Class<?> parameterType) {
//        XMLScriptBuilder builder = new XMLScriptBuilder(configuration, script, parameterType);
//        return builder.parseScriptNode();
        return null;
    }

    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        if (script.startsWith("<script>")) {
            XPathParser parser = new XPathParser(script, false, configuration.getVariables(), new XMLMapperEntityResolver());
        }
        return null;
    }
}
