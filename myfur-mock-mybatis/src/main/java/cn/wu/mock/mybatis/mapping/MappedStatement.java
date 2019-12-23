package cn.wu.mock.mybatis.mapping;

import cn.wu.mock.mybatis.session.Configuration;

public final class MappedStatement {

    private String resource;
    private Configuration configuration;
    private String id;
    private Integer fetchSize;
    private Integer timeout;
    private StatementType statementType;
    private ResultSetType resultSetType;
    private SqlSource sqlSource;
}
