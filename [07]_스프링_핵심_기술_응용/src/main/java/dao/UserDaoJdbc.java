package dao;

import domain.Level;
import domain.User;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

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

    private String sqlAdd;
//    "insert into users(id, name, password, level, login, recommend) values (?, ?, ?, ?, ?, ?)"

    public UserDaoJdbc() {
    }

    /**
     * 수동 DI 방식
     */
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void setSqlAdd(String sqlAdd) {
        this.sqlAdd = sqlAdd;
    }

    @Override
    public void add(final User user) throws DuplicateKeyException {
        jdbcTemplate.update(
            sqlAdd,
            user.getId(), user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend()
        );
    }

    @Override
    public User get(String id) {
        return jdbcTemplate.queryForObject(
            "select * from users where id = ?",
            userMapper,
            id
        );
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from users");
    }

    @Override
    public int getCount() {
        // queryForInt 는 Deprecated 됨
        return jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update(
            "update users set name = ?, password = ?, level = ?, login = ?, recommend = ? "
                + "where id = ? ",
            user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(),
            user.getId()
        );
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("select * from users order by id", userMapper);
    }
}
