package me.june.spring.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import me.june.spring.domain.User;

public abstract class UserDao {

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection conn = getConnection();

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
        Connection conn = getConnection();

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

    public abstract Connection getConnection() throws ClassNotFoundException, SQLException;

    /**
     * Self-Test 용 Main 메소드
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao dao = new NUserDao();

        User user = new User();
        user.setId("ncucu");
        user.setName("엔꾸꾸");
        user.setPassword("패스워드");

        dao.add(user);

        System.out.println(user.getId() + "등록 성공");

        User findUser = dao.get(user.getId());
        System.out.println(findUser.getName());
    }
}
