package cn.wu.mock.mybatis.transaction.managed;

import cn.wu.mock.mybatis.session.TransactionIsolationLevel;
import cn.wu.mock.mybatis.transaction.Transaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ManagedTransaction implements Transaction {

    private DataSource dataSource;
    private TransactionIsolationLevel level;
    private Connection connection;
    private final boolean closeConnection;

    public ManagedTransaction(Connection connection, boolean closeConnection) {
        this.connection = connection;
        this.closeConnection = closeConnection;
    }

    public ManagedTransaction(DataSource ds, TransactionIsolationLevel level, boolean closeConnection) {
        this.dataSource = ds;
        this.level = level;
        this.closeConnection = closeConnection;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (this.connection == null) {
            openConnection();
        }
        return this.connection;
    }

    protected void openConnection() throws SQLException{
        this.connection = this.dataSource.getConnection();
        if (this.level != null) {
            this.connection.setTransactionIsolation(this.level.getLevel());
        }
    }

    @Override
    public void commit() throws SQLException {
        // Does nothing
    }

    @Override
    public void rollback() throws SQLException {
        // Does nothing
    }

    @Override
    public void close() throws SQLException {
        if (this.closeConnection && this.connection != null) {
            this.connection.close();
        }
    }

    @Override
    public Integer getTimeout() throws SQLException {
        return null;
    }
}
