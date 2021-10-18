package dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import domain.User;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
/**
 * JUnit 은 ApplicationContext 를 캐싱해둔다
 * 이는 동일한 클래스에서만 적용되는것이 아닌, 클래스 간에도 적용됨
 */
@ContextConfiguration(
//    locations = "/applicationContext.xml" // xml 방식
    classes = DaoFactory.class // JavaConfig 방식
)
class UserDaoTest {

    @Autowired
    private ApplicationContext context;

    private UserDao dao;
    private User user1;
    private User user2;
    private User user3;

    @BeforeAll
    static void staticSetUp() {

    }

    @BeforeEach
    void setUp() {
        dao = context.getBean("userDao", UserDao.class);
        user1 = new User("ncucu", "엔꾸꾸", "password");
        user2 = new User("ncucu1", "엔꾸꾸1", "password1");
        user3 = new User("ncucu2", "엔꾸꾸2", "password2");

        System.out.println(this.context); // applicationContext 의 주소는 동일하지만, testClass 인스턴스는 매번 바뀐다.
        System.out.println(this);
    }

    @Test
    void addAndGet() throws Exception {
        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

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