package cn.wu.mock.mybatis.transaction.jdbc;

import cn.wu.mock.mybatis.session.TransactionIsolationLevel;
import cn.wu.mock.mybatis.transaction.Transaction;
import cn.wu.mock.mybatis.transaction.TransactionFactory;

import javax.sql.DataSource;
import java.sql.Connection;

public class JdbcTransactionFactory implements TransactionFactory {

    @Override
    public Transaction newTransaction(Connection conn) {
        return new JdbcTransaction(conn);
    }

    @Override
    public Transaction newTransaction(DataSource ds, TransactionIsolationLevel level, boolean autoCommit) {
        return new JdbcTransaction(ds, level, autoCommit);
    }
}
