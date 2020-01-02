package com.wu.mock.spring.mybatis;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;

import javax.sql.DataSource;

public class MyBatisExceptionTranslator implements PersistenceExceptionTranslator {

    private final DataSource dataSource;

    private SQLExceptionTranslator exceptionTranslator;

    public MyBatisExceptionTranslator(DataSource dataSource, boolean exceptionTranslatorLazyInit) {
        this.dataSource = dataSource;

        if (!exceptionTranslatorLazyInit) {
            this.initExceptionTransla
        }
    }

    @Override
    public DataAccessException translateExceptionIfPossible(RuntimeException ex) {
        return null;
    }
}
