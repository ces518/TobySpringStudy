package dao;

import domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;

public class UserDao {

    private DataSource dataSource;

    public UserDao() {
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        StatementStrategy stmt = new AddStatement(user);
        jdbcContextWithStatementStrategy(stmt);
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        User user = null;
        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(
                "select * from users where id = ?");
            ps.setString(1, id);

            rs = ps.executeQuery();

            user = null;
            if (rs.next()) {
                user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null ){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


        if (user == null) {
            throw new EmptyResultDataAccessException(1);
        }

        return user;
    }

    public void deleteAll() throws SQLException {
        StatementStrategy st = new DeleteAllStatement();
        jdbcContextWithStatementStrategy(st);
    }

    public int getCount() throws SQLException {
        Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement("select count(*) from users");

        ResultSet rs = ps.executeQuery();
        rs.next();

        int count = rs.getInt(1);

        rs.close();
        ps.close();
        conn.close();

        return count;
    }

    protected PreparedStatement makeStatement(Connection c) throws SQLException {
        return null;
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dataSource.getConnection();

            // 전략패턴 적용
            ps = stmt.makePreparedStatement(conn);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
