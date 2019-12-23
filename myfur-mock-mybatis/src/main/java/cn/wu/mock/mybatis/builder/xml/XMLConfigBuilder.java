package cn.wu.mock.mybatis.builder.xml;

import cn.wu.mock.mybatis.builder.BaseBuilder;
import cn.wu.mock.mybatis.executor.ErrorContext;
import cn.wu.mock.mybatis.parsing.XPathParser;
import cn.wu.mock.mybatis.session.Configuration;

import java.io.Reader;
import java.util.Properties;

public class XMLConfigBuilder extends BaseBuilder {

    public XMLConfigBuilder(Reader reader, String environment, Properties props) {
        this(new XPathParser(reader,true,props,new XMLMapperEntityResolver()), environment,props);
    }

    private XMLConfigBuilder(XPathParser parser, String environment, Properties props) {
        super(new Configuration());
        ErrorContext.instance().resource("SQL Mapper Configuration");
        this.configuration.setVariables(props);

    }


}
