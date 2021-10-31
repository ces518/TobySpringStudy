package service;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

import dao.DaoFactory;
import dao.UserDao;
import domain.Level;
import domain.User;
import java.util.ArrayList;
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
            new User("ncucu", "엔꾸꾸", "p", Level.BASIC, 49, 0),
            new User("ncucu1", "엔꾸꾸1", "p1", Level.BASIC, 50, 0),
            new User("ncucu2", "엔꾸꾸2", "p2", Level.SILVER, 60, 29),
            new User("ncucu3", "엔꾸꾸3", "p3", Level.SILVER, 60, 30),
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

        checkLevel(users.get(0), Level.BASIC);
        checkLevel(users.get(1), Level.SILVER);
        checkLevel(users.get(2), Level.SILVER);
        checkLevel(users.get(3), Level.GOLD);
        checkLevel(users.get(4), Level.GOLD);
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

    private void checkLevel(User user, Level expectedLevel) {
        User updatedUser = userDao.get(user.getId());
        assertThat(updatedUser.getLevel()).isEqualTo(expectedLevel);
    }
}