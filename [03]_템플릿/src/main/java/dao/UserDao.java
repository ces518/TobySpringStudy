package dao;

import domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

public class UserDao {

    private JdbcTemplate jdbcTemplate;

    public UserDao() {
    }

    /**
     * 수동 DI 방식
     */
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(final User user) throws ClassNotFoundException, SQLException {
        // 익명 클래스로 개선
//        jdbcTemplate.workWithStatementStrategy(new StatementStrategy() {
//
//            @Override
//            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
//                PreparedStatement ps = c.prepareStatement(
//                    "insert into users (id, name, password) values (?, ?, ?)");
//                ps.setString(1, user.getId());
//                ps.setString(2, user.getName());
//                ps.setString(3, user.getPassword());
//                return ps;
//            }
//        });
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//
//        User user = null;
//        try {
//            conn = dataSource.getConnection();
//            ps = conn.prepareStatement(
//                "select * from users where id = ?");
//            ps.setString(1, id);
//
//            rs = ps.executeQuery();
//
//            user = null;
//            if (rs.next()) {
//                user = new User();
//                user.setId(rs.getString("id"));
//                user.setName(rs.getString("name"));
//                user.setPassword(rs.getString("password"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if (rs != null) {
//                try {
//                    rs.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (ps != null) {
//                try {
//                    ps.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (conn != null) {
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        if (user == null) {
//            throw new EmptyResultDataAccessException(1);
//        }

        return null;
    }

    public void deleteAll() throws SQLException {
        jdbcTemplate.update("delete from users");
    }

    public int getCount() throws SQLException {
//        Connection conn = dataSource.getConnection();
//        PreparedStatement ps = conn.prepareStatement("select count(*) from users");
//
//        ResultSet rs = ps.executeQuery();
//        rs.next();
//
//        int count = rs.getInt(1);
//
//        rs.close();
//        ps.close();
//        conn.close();

        return 0;
    }

    protected PreparedStatement makeStatement(Connection c) throws SQLException {
        return null;
    }
}
