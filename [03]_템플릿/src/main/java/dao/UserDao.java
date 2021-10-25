package dao;

import domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

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
        jdbcTemplate.update("insert into users(id, name, password) values (?, ?, ?)",
            user.getId(), user.getName(), user.getPassword());
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        return jdbcTemplate.queryForObject("select * from users where id = ?", new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        }, id);
    }

    public void deleteAll() throws SQLException {
        jdbcTemplate.update("delete from users");
    }

    public int getCount() throws SQLException {
        // queryForInt 는 Deprecated 됨
        return jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }

    protected PreparedStatement makeStatement(Connection c) throws SQLException {
        return null;
    }
}
