package service;

import static domain.DefaultUserLevelUpgradePolicy.MIN_LOGIN_COUNT_FOR_SILVER;
import static domain.DefaultUserLevelUpgradePolicy.MIN_RECOMMEND_FOR_GOLD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import dao.DaoFactory;
import dao.UserDao;
import domain.Level;
import domain.User;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
    classes = DaoFactory.class
)
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    List<User> users;

    @BeforeEach
    void setUp() {
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
        userService.upgradeLevels();

        checkLevel(users.get(0), false);
        checkLevel(users.get(1), true);
        checkLevel(users.get(2), false);
        checkLevel(users.get(3), true);
        checkLevel(users.get(4), false);
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
    static class TestUserService extends UserService {
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
}