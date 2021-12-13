package me.june.test;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
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

    /**
     * 트랜잭션 경셰 설정을 위해 사용한 방법도 동일하다.
     * 마치 AOP 를 적용한 것 처럼 트랜잭션 기능을 테스트 메소드에 적용 가능하게 지원해준다.
     */
    @Test
    @Transactional
    @Rollback(false) // 롤백이 되지 않게 지정하는 방법
    void transactional() {
        // DB 작업..
    }

    /**
     * 트랜잭션 시작전/완전히 종료된 후에 작업을 지원하기 위한 애노테이션
     */
    @BeforeTransaction
    void setUpBeforeTx() {

    }

    @AfterTransaction
    void tearDownAfterTx() {

    }

    static class JpaDao {

        public List<User> findUsers() {
            return Collections.emptyList();
        }
    }

    static class User {

    }
}
