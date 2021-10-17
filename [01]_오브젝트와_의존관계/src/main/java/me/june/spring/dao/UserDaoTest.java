package me.june.spring.dao;

import java.sql.SQLException;
import me.june.spring.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class UserDaoTest {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext(
            "applicationContext.xml");
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
//            DaoFactory.class);
        UserDao dao = context.getBean("userDao", UserDao.class);
        UserDao dao2 = context.getBean("userDao", UserDao.class);

        System.out.println(dao == dao2); // true

//        User user = new User();
//        user.setId("ncucu");
//        user.setName("엔꾸꾸");
//        user.setPassword("패스워드");
//
//        dao.add(user);
//
//        System.out.println(user.getId() + "등록 성공");
//
//        User findUser = dao.get(user.getId());
//        System.out.println(findUser.getName());
    }
}
