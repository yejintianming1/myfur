package cn.wu.mock.mybatis.session;

import cn.wu.mock.mybatis.builder.xml.XMLConfigBuilder;
import cn.wu.mock.mybatis.exceptions.ExceptionFactory;
import cn.wu.mock.mybatis.executor.ErrorContext;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(Reader reader) {
        return build(reader, null)
    }

    public SqlSessionFactory build(Reader reader, String environment, Properties properties) {

        try {
            XMLConfigBuilder parser = new XMLConfigBuilder(reader, environment,properties);
        } catch (Exception e) {
            throw ExceptionFactory.wrapException("Error building SqlSession.", e);
        } finally {
            ErrorContext.instance().reset();
            try {
                reader.close();
            } catch (IOException e) {
                // ignore this error
            }
        }

    }
}
