package cn.yto.mock.mybatis.transaction;

import java.sql.Connection;
import java.sql.SQLException;

public interface Transaction {

    Connection getConnection() throws SQLException;
}
