package me.june.test;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TransactionTest {

    @Autowired
    JpaDao dao;

    /**
     * 트랜잭션이 없는 단독 DAO 테스트, JdbcTemplate 은 문제가 없지만, JPA/Hibernate 의 경우 문제가 된다.
     * 반드시 트랜잭션 내에서 동작해야 함
     */
    @Test
    void query() {
        List<User> users = dao.findUsers();
    }

    static class JpaDao {

        public List<User> findUsers() {
            return Collections.emptyList();
        }
    }

    static class User {

    }
}
