package service;

import static domain.DefaultUserLevelUpgradePolicy.MIN_LOGIN_COUNT_FOR_SILVER;
import static domain.DefaultUserLevelUpgradePolicy.MIN_RECOMMEND_FOR_GOLD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import dao.DaoFactory;
import dao.UserDao;
import domain.Level;
import domain.User;
import domain.UserLevelUpgradePolicy;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import mail.DummyMailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
    classes = DaoFactory.class
)
@DirtiesContext
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    UserDao userDao;

    @Autowired
    DataSource dataSource;

    @Autowired
    UserLevelUpgradePolicy policy;

    @Autowired
    PlatformTransactionManager transactionManager;

    MailSender mailSender;

    List<User> users;

    @BeforeEach
    void setUp() {
        mailSender = new DummyMailSender();
        users = List.of(
            new User("ncucu", "엔꾸꾸", "p", Level.BASIC, MIN_LOGIN_COUNT_FOR_SILVER - 1, 0),
            new User("ncucu1", "엔꾸꾸1", "p1", Level.BASIC, MIN_LOGIN_COUNT_FOR_SILVER, 0),
            new User("ncucu2", "엔꾸꾸2", "p2", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD - 1),
            new User("ncucu3", "엔꾸꾸3", "p3", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD),
            new User("ncucu4", "엔꾸꾸4", "p4", Level.GOLD, 100, 100)
        );
    }

    @Test
    void bean() {
        assertThat(userService).isNotNull();
    }

    @Test
    void upgradeLevels() {
        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }
        MockMailSender mockMailSender = new MockMailSender();
        userServiceImpl.setMailSender(mockMailSender);
        userServiceImpl.upgradeLevels();

        checkLevel(users.get(0), false);
        checkLevel(users.get(1), true);
        checkLevel(users.get(2), false);
        checkLevel(users.get(3), true);
        checkLevel(users.get(4), false);

        List<String> request = mockMailSender.getRequests();
        assertThat(request.size()).isEqualTo(2);
        assertThat(request.get(0)).isEqualTo(users.get(1).getEmail());
        assertThat(request.get(1)).isEqualTo(users.get(3).getEmail());
    }

    @Test
    void add() {
        userDao.deleteAll();

        // GOLD 레벨로 지정되어 있다면 레벨이 초기화되지 않아야됨
        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(0); // Level 이 지정되어 있지 않다면 BASIC 으로 지정
        userWithoutLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertThat(userWithLevelRead.getLevel()).isEqualTo(userWithLevel.getLevel());
        assertThat(userWithoutLevelRead.getLevel()).isEqualTo(Level.BASIC);
    }

    @Test
    void upgradeAllOrNothing() throws Exception {
        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }

        TestUserService service = new TestUserService(users.get(3).getId());
        service.setUserDao(this.userDao);
        service.setUserLevelUpgradePolicy(this.policy);
        service.setDataSource(this.dataSource);
        service.setTransactionManager(this.transactionManager);
        service.setMailSender(this.mailSender);
        try {
            service.upgradeLevels();
            fail("TestUserServiceException expected"); // 예외가 발생하지 않는다면 실패
        } catch (TestUserServiceException ignored) {}
        checkLevel(users.get(1), false);
    }

    /**
     * 테스트의 의도를 분명히 드러내도록 변경 기존의 Level 을 인자로 받는 테스트는, 의도를 파악하기가 힘든 감이 있었다. upgrade 여부를 인자로 받고, Level
     * 객체를 통해 기대하는 Level 값을 가져오도록 변경한다.
     */
    private void checkLevel(User user, boolean upgraded) {
        User updatedUser = userDao.get(user.getId());
        Level expectedLevel = upgraded ? user.getLevel().nextLevel() : user.getLevel();
        assertThat(updatedUser.getLevel()).isEqualTo(expectedLevel);
    }

    // 테스트용 UserService
    static class TestUserService extends UserServiceImpl {
        private String id;

        public TestUserService(String id) {
            this.id = id;
        }

        @Override
        protected void upgradeLevel(User user) {
            if (user.getId().equals(this.id)) {
                throw new TestUserServiceException();
            }
            super.upgradeLevel(user);
        }
    }

    static class TestUserServiceException extends RuntimeException {}

    static class MockMailSender implements MailSender {
        private List<String> requests = new ArrayList<>();

        public List<String> getRequests() {
            return requests;
        }

        @Override
        public void send(SimpleMailMessage simpleMessage) throws MailException {
            requests.add(simpleMessage.getTo()[0]);
        }

        @Override
        public void send(SimpleMailMessage... simpleMessages) throws MailException {
        }
    }
}