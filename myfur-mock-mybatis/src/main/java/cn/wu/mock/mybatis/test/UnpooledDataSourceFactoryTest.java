package cn.wu.mock.mybatis.test;

import cn.wu.mock.mybatis.datasource.unpooled.UnpooledDataSourceFactory;
import cn.wu.mock.mybatis.type.JdbcType;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UnpooledDataSourceFactoryTest {

    public static void main(String[] args) throws SQLException {
        UnpooledDataSourceFactory unpooledDataSourceFactory = new UnpooledDataSourceFactory();
        Properties properties = new Properties();
        properties.setProperty("driver","com.mysql.cj.jdbc.Driver");
        properties.setProperty("url","jdbc:mysql://localhost/demo?serverTimezone=UTC");
        properties.setProperty("username","root");
        properties.setProperty("password","root");

        unpooledDataSourceFactory.setProperties(properties);
        DataSource dataSource = unpooledDataSourceFactory.getDataSource();

        Connection connection = dataSource.getConnection();
        PreparedStatement pst = connection.prepareStatement("select * from user where id = ?");
        pst.setLong(1,1L);
        pst.execute();
        ResultSet rs = pst.getResultSet();

        while (rs == null) {
            if (pst.getMoreResults()) {
                rs = pst.getResultSet();
            } else {
                if (pst.getUpdateCount() == -1) {
                    break;
                }
            }
        }

        List<String> columnNames = new ArrayList<>();
        List<String> classNames = new ArrayList<>();
        List<JdbcType> jdbcTypes = new ArrayList<>();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i < columnCount; i++) {
            //是否使用columnLabel
            columnNames.add(metaData.getColumnLabel(i));
            jdbcTypes.add(JdbcType.forCode(metaData.getColumnType(i)));
            classNames.add(metaData.getColumnClassName(i));
        }

        System.out.println(columnNames);
        System.out.println(jdbcTypes);
        System.out.println(classNames);



//        while (rs.next()) {
//            long id = rs.getLong(1);
//            String name = rs.getString(2);
//            int age = rs.getInt(3);
//            System.out.println("id:"+id+"|name:"+name+"|age:"+age);
//        }


    }


}
