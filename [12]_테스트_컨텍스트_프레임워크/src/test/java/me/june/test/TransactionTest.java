package me.june.test;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

public class TransactionTest {

    @Autowired
    JpaDao dao;

    @Autowired
    PlatformTransactionManager transactionManager;

    /**
     * 트랜잭션이 없는 단독 DAO 테스트, JdbcTemplate 은 문제가 없지만, JPA/Hibernate 의 경우 문제가 된다.
     * 반드시 트랜잭션 내에서 동작해야 함
     */
    @Test
    void query() {
        List<User> users = dao.findUsers();
    }

    /**
     * 롤백 테스트를 위해 TransactionTemplate 을 사용하는 방법
     */
    @Test
    void transactionTemplate() {
        new TransactionTemplate(transactionManager).execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {
                status.setRollbackOnly();
                // .. DB 작업
                return null;
            }
        });
    }

    static class JpaDao {

        public List<User> findUsers() {
            return Collections.emptyList();
        }
    }

    static class User {

    }
}
