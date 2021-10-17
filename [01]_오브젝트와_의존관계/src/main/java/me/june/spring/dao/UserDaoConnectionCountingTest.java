package me.june.spring.dao;

import me.june.spring.infra.CountingConnectionMaker;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UserDaoConnectionCountingTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
            CountingDaoFactory.class);
        UserDao dao = context.getBean("userDao", UserDao.class);

        CountingConnectionMaker connectionMaker = context.getBean("connectionMaker",
            CountingConnectionMaker.class);

        System.out.println(connectionMaker.getCounter());
    }
}
