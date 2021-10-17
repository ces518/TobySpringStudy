package me.june.spring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import me.june.spring.domain.User;
import me.june.spring.infra.ConnectionMaker;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UserDao {

    private ConnectionMaker connectionMaker;

    public UserDao() {
    }

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void setConnectionMaker(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    /**
     * 의존관계 검색
     * 주입과 검색의 차이는, 주입은 IoC 컨테이너가 의존관계에 있는 객체를 생성자 등을 통해 주입해 주지만
     * 검색의 경우에는 IoC 컨테이너에게 직접 요청한다.
     */
//    public UserDao() {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
//            DaoFactory.class);
//        this.connectionMaker = context.getBean("connectionMaker", ConnectionMaker.class);
//    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection conn = connectionMaker.makeConnection();

        PreparedStatement ps = conn.prepareStatement(
            "insert into users (id, name, password) values (?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        conn.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection conn = connectionMaker.makeConnection();

        PreparedStatement ps = conn.prepareStatement(
            "select * from users where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();

        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        conn.close();
        return user;
    }
}
