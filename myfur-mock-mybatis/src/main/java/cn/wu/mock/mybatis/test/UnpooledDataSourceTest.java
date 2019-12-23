package cn.wu.mock.mybatis.test;

import cn.wu.mock.mybatis.datasource.unpooled.UnpooledDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UnpooledDataSourceTest {

    public static void main(String[] args) throws SQLException {
        UnpooledDataSource dataSource = new UnpooledDataSource();
        dataSource.setDriver("com.mysql.cj.jdbc.Driver");//com.mysql.jdbc.Driver
        dataSource.setUrl("jdbc:mysql://localhost/demo?serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        Connection connection = dataSource.getConnection();
        PreparedStatement pst = connection.prepareStatement("select * from user where id = ?");
        pst.setLong(1,1L);
        pst.execute();
        ResultSet resultSet = pst.getResultSet();
        while (resultSet.next()) {
            long id = resultSet.getLong(1);
            String name = resultSet.getString(2);
            int age = resultSet.getInt(3);
            System.out.println("id:"+id+"|name:"+name+"|age:"+age);
        }


    }
}
