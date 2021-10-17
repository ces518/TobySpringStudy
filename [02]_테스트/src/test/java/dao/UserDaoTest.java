package dao;

import static org.assertj.core.api.Assertions.assertThat;

import domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class UserDaoTest {

    @Test
    void addAndGet() throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
            DaoFactory.class);
        UserDao dao = context.getBean("userDao", UserDao.class);

        User user = new User();
        user.setId("ncucu");
        user.setName("엔꾸꾸");
        user.setPassword("패스워드");

        dao.add(user);

        User findUser = dao.get(user.getId());

        assertThat(user.getName()).isEqualTo(findUser.getName());
    }
}