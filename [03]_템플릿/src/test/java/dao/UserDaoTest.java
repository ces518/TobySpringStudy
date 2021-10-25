package dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import domain.User;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

//@ExtendWith(SpringExtension.class)

/**
 * JUnit 은 ApplicationContext 를 캐싱해둔다
 * 이는 동일한 클래스에서만 적용되는것이 아닌, 클래스 간에도 적용됨
 */
//@ContextConfiguration(
////    locations = "/applicationContext.xml" // xml 방식
//    classes = DaoFactory.class // JavaConfig 방식
//)
//@DirtiesContext // 테스트 메소드에서 애플리케이션 컨텍스트의 구성이 바뀜을 알린다. (애플리케이션 컨텍스트를 공유하지 않음)
class UserDaoTest {

    private UserDao dao;

    private User user1;
    private User user2;
    private User user3;

    @BeforeAll
    static void staticSetUp() {

    }

    @BeforeEach
    void setUp() {
        dao = new UserDao();
        // TestDI
        // SingleConnectionDataSource 는 커넥션을 하나만 만들어서 계속 사용하기 때문에 매우 빠르다.
        DataSource dataSource = new SingleConnectionDataSource(
            "jdbc:mysql://localhost/test", "root", "password", true);
        dao.setDataSource(dataSource);

        user1 = new User("ncucu", "엔꾸꾸", "password");
        user2 = new User("ncucu1", "엔꾸꾸1", "password1");
        user3 = new User("ncucu2", "엔꾸꾸2", "password2");

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

    @Test
    void getAll() throws Exception {
        dao.deleteAll();

        assertThat(dao.getAll().size()).isEqualTo(0);

        dao.add(user1);
        List<User> users1 = dao.getAll();
        assertThat(users1.size()).isEqualTo(1);
        checkSameUser(user1, users1.get(0));

        dao.add(user2);
        List<User> users2 = dao.getAll();
        checkSameUser(user1, users2.get(0));
        checkSameUser(user2, users2.get(1));

        dao.add(user3);
        List<User> users3 = dao.getAll();
        checkSameUser(user1, users3.get(0));
        checkSameUser(user2, users3.get(1));
        checkSameUser(user3, users3.get(2));
    }

    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getId()).isEqualTo(user2.getId());
        assertThat(user1.getName()).isEqualTo(user2.getName());
        assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
    }
}