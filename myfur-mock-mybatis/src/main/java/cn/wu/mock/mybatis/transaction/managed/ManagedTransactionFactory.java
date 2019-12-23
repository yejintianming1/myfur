package cn.wu.mock.mybatis.transaction.managed;

import cn.wu.mock.mybatis.session.TransactionIsolationLevel;
import cn.wu.mock.mybatis.transaction.Transaction;
import cn.wu.mock.mybatis.transaction.TransactionFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

public class ManagedTransactionFactory implements TransactionFactory {

    private boolean closeConnection = true;

    @Override
    public void setProperties(Properties props) {
        if (props != null) {
            String closeConnectionProperty = props.getProperty("closeConnection");
            if(closeConnectionProperty != null) {
                closeConnection = Boolean.valueOf(closeConnectionProperty);
            }
        }
    }

    @Override
    public Transaction newTransaction(Connection conn) {
        return new ManagedTransaction(conn, closeConnection);
    }

    @Override
    public Transaction newTransaction(DataSource ds, TransactionIsolationLevel level, boolean autoCommit) {
        return new ManagedTransaction(ds,level,closeConnection);
    }
}
