package cn.yto.mock.mybatis.transaction.jdbc;

import cn.yto.mock.mybatis.transaction.Transaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcTransaction implements Transaction {

    protected Connection connection;
    protected DataSource dataSource;

    public JdbcTransaction(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null) {//没有连接则新打开一个
            openConnection();
        }
        return connection;
    }

    protected void openConnection() throws SQLException {
        connection = dataSource.getConnection();
//        connection.setAutoCommit(false);
    }
}
