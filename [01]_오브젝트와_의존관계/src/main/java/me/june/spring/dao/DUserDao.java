package me.june.spring.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import me.june.spring.infra.ConnectionMaker;

public class DUserDao extends UserDao {

    public DUserDao(ConnectionMaker connectionMaker) {
//        super(connectionMaker);
    }

//    @Override
//    public Connection getConnection() throws ClassNotFoundException, SQLException {
//        Class.forName("com.mysql.jdbc.Driver");
//        return DriverManager.getConnection("jdbc:mysql://localhost/spring_batch",
//            "root", "password");
//    }
}
