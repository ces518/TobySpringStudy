package dao;

import domain.Level;
import domain.User;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import sqlservice.SqlService;

public class UserDaoJdbc implements UserDao {

    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userMapper =
        (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            user.setLevel(Level.valueOf(rs.getInt("level")));
            user.setLogin(rs.getInt("login"));
            user.setRecommend(rs.getInt("recommend"));
            return user;
        };

    private SqlService sqlService;
//    "insert into users(id, name, password, level, login, recommend) values (?, ?, ?, ?, ?, ?)"

    public UserDaoJdbc() {
    }

    /**
     * 수동 DI 방식
     */
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void setSqlService(SqlService sqlService) {
        this.sqlService = sqlService;
    }

    @Override
    public void add(final User user) throws DuplicateKeyException {
        jdbcTemplate.update(
            sqlService.getSql("userAdd"),
            user.getId(), user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend()
        );
    }

    @Override
    public User get(String id) {
        return jdbcTemplate.queryForObject(
            sqlService.getSql("userGet"),
            userMapper,
            id
        );
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(sqlService.getSql("userDelete"));
    }

    @Override
    public int getCount() {
        return jdbcTemplate.queryForObject(sqlService.getSql("userCount"), Integer.class);
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update(
            sqlService.getSql("userUpdate"),
            user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(),
            user.getId()
        );
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query(sqlService.getSql("userGetAll"), userMapper);
    }
}
