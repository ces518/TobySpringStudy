package service;

import dao.UserDao;
import domain.Level;
import domain.User;
import domain.UserLevelUpgradePolicy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionSynchronizationUtils;

public class UserService {

    UserLevelUpgradePolicy policy;

    UserDao userDao;

    DataSource dataSource;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setUserLevelUpgradePolicy(UserLevelUpgradePolicy policy) {
        this.policy = policy;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void upgradeLevels() {
        TransactionSynchronizationManager.initSynchronization();
        Connection c = DataSourceUtils.getConnection(dataSource);
        try {
            c.setAutoCommit(false);

            List<User> users = userDao.getAll();
            for (User user : users) {
                if (canUpgradeLevel(user)) {
                    upgradeLevel(user);
                }
            }

            c.commit();
        } catch (Exception e) {
            try {
                c.rollback();
            } catch (SQLException ex) {
            }
            try {
                throw e;
            } catch (SQLException ex) {
            }
        } finally {
            DataSourceUtils.releaseConnection(c, dataSource);
            TransactionSynchronizationManager.unbindResource(dataSource);
            TransactionSynchronizationManager.clearSynchronization();
        }

    }

    public void add(User user) {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }

    private boolean canUpgradeLevel(User user) {
        return policy.canUpgradeLevel(user);
    }

    protected void upgradeLevel(User user) {
        policy.upgradeLevel(user);
        userDao.update(user);
    }
}
