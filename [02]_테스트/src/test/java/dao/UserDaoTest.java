package dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import domain.User;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

class UserDaoTest {

    private UserDao dao;

    @BeforeEach
    void setUp() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
            DaoFactory.class);
        dao = context.getBean("userDao", UserDao.class);
    }

    @Test
    void addAndGet() throws Exception {
        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        User user1 = new User("ncucu", "엔꾸꾸", "password");
        User user2 = new User("ncucu1", "엔꾸꾸1", "password1");

        dao.add(user1);
        dao.add(user2);
        assertThat(dao.getCount()).isEqualTo(2);

        User findUser = dao.get(user1.getId());

        assertThat(user1.getId()).isEqualTo(findUser.getId());
        assertThat(user1.getName()).isEqualTo(findUser.getName());
        assertThat(user1.getPassword()).isEqualTo(findUser.getPassword());
    }

    @Test
    void getUserFailure() throws SQLException {
        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        assertThrows(EmptyResultDataAccessException.class, () -> dao.get("unknown_id"));
    }

    @Test
    void count() throws Exception {
        User user1 = new User("ncucu", "엔꾸꾸", "password");
        User user2 = new User("ncucu1", "엔꾸꾸1", "password1");
        User user3 = new User("ncucu2", "엔꾸꾸2", "password2");

        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        dao.add(user1);
        assertThat(dao.getCount()).isEqualTo(1);

        dao.add(user2);
        assertThat(dao.getCount()).isEqualTo(2);

        dao.add(user3);
        assertThat(dao.getCount()).isEqualTo(3);
    }
}