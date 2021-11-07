package service;

import domain.User;
import java.util.List;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class UserServiceTx implements UserService {

    PlatformTransactionManager transactionManager;
    UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public void add(User user) {
        userService.add(user);
    }

    @Override
    public void upgradeLevels() {
        // 트랜잭션 시작
        TransactionStatus status = transactionManager.getTransaction(
            new DefaultTransactionDefinition());
        try {
            userService.upgradeLevels();
            // 트랜잭션 커밋
            transactionManager.commit(status);
        } catch (RuntimeException e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    @Override
    public User get(String id) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
