package cn.wu.mock.mybatis.mapping;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

public interface DatabaseIdProvider {

    default void setProperties(Properties p) {
        // NOP
    }

    String getDatabaseId(DataSource dataSource) throws SQLException;
}
