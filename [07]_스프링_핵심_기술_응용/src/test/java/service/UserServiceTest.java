package service;

import static domain.DefaultUserLevelUpgradePolicy.MIN_LOGIN_COUNT_FOR_SILVER;
import static domain.DefaultUserLevelUpgradePolicy.MIN_RECOMMEND_FOR_GOLD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import config.TestApplicationContext;
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
import org.mockito.ArgumentCaptor;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
    classes = TestApplicationContext.class
)
@DirtiesContext
@Transactional // 테스트의 경우 기본이 롤백이다.
public class UserServiceTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    UserService userService;

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
        UserServiceImpl userService = new UserServiceImpl();

        MockUserDao mockUserDao = new MockUserDao(users);
        userService.setUserDao(mockUserDao);
        userService.setUserLevelUpgradePolicy(policy);

        MockMailSender mockMailSender = new MockMailSender();
        userService.setMailSender(mockMailSender);

        userService.upgradeLevels();

        List<User> updated = mockUserDao.getUpdated();
        assertThat(updated.size()).isEqualTo(2);
        checkUserAndLevel(updated.get(0), "ncucu1", Level.SILVER);
        checkUserAndLevel(updated.get(1), "ncucu3", Level.GOLD);

        List<String> request = mockMailSender.getRequests();
        assertThat(request.size()).isEqualTo(2);
        assertThat(request.get(0)).isEqualTo(users.get(1).getEmail());
        assertThat(request.get(1)).isEqualTo(users.get(3).getEmail());
    }

    @Test
    void mockUpgradeLevels() throws Exception {
        UserServiceImpl userService = new UserServiceImpl();

        UserDao mockUserDao = mock(UserDao.class);
        when(mockUserDao.getAll()).thenReturn(this.users);
        userService.setUserDao(mockUserDao);

        MailSender mockMailSender = mock(MailSender.class);
        userService.setMailSender(mockMailSender);
        userService.setUserLevelUpgradePolicy(policy);

        userService.upgradeLevels();

        verify(mockUserDao, times(2)).update(any(User.class));
        verify(mockUserDao).update(users.get(1));
        assertThat(users.get(1).getLevel()).isEqualTo(Level.SILVER);
        verify(mockUserDao).update(users.get(3));
        assertThat(users.get(3).getLevel()).isEqualTo(Level.GOLD);

        ArgumentCaptor<SimpleMailMessage> mailMessageArg = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mockMailSender, times(2)).send(mailMessageArg.capture());
        List<SimpleMailMessage> mailMessages = mailMessageArg.getAllValues();
        assertThat(mailMessages.get(0).getTo()[0]).isEqualTo(users.get(1).getEmail());
        assertThat(mailMessages.get(1).getTo()[0]).isEqualTo(users.get(3).getEmail());
    }

    private void checkUserAndLevel(User updated, String expected, Level expectedLevel) {
        assertThat(updated.getId()).isEqualTo(expected);
        assertThat(updated.getLevel()).isEqualTo(expectedLevel);
    }

    @Test
    @Rollback(value = false) // 롤백하지 않도록 지정
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
    @DirtiesContext
    void upgradeAllOrNothing() throws Exception {
        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }

        TestUserServiceImpl service = new TestUserServiceImpl();
        service.setUserDao(this.userDao);
        service.setUserLevelUpgradePolicy(this.policy);
        service.setDataSource(this.dataSource);
        service.setTransactionManager(this.transactionManager);
        service.setMailSender(this.mailSender);

        ProxyFactoryBean txProxyFactoryBean = context.getBean("&userService", ProxyFactoryBean.class);
        txProxyFactoryBean.setTarget(service);
        UserService txUserService = (UserService) txProxyFactoryBean.getObject();

        try {
            txUserService.upgradeLevels();
            fail("TestUserServiceException expected"); // 예외가 발생하지 않는다면 실패
        } catch (TestUserServiceException ignored) {
        }
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
    public static class TestUserServiceImpl extends UserServiceImpl {

        private String id = "ncucu3";

        @Override
        protected void upgradeLevel(User user) {
            if (user.getId().equals(this.id)) {
                throw new TestUserServiceException();
            }
            super.upgradeLevel(user);
        }
    }

    public static class TestUserServiceException extends RuntimeException {

    }

    public static class MockMailSender implements MailSender {

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

    public static class MockUserDao implements UserDao {

        private List<User> users;
        private List<User> updated = new ArrayList<>();

        public MockUserDao(List<User> users) {
            this.users = users;
        }

        /**
         * Stub 제공
         */
        public List<User> getUpdated() {
            return updated;
        }

        /**
         * Mock Object 제공
         */
        @Override
        public List<User> getAll() {
            return users;
        }

        @Override
        public void update(User user) {
            updated.add(user);
        }

        @Override
        public void add(User user) {
            throw new UnsupportedOperationException();
        }

        @Override
        public User get(String id) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void deleteAll() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getCount() {
            throw new UnsupportedOperationException();
        }


    }
}