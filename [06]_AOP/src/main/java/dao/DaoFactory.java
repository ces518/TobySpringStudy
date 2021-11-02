package dao;

import domain.DefaultUserLevelUpgradePolicy;
import domain.UserLevelUpgradePolicy;
import factorybean.MessageFactoryBean;
import javax.sql.DataSource;
import mail.DummyMailSender;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;
import proxyfactorybean.NameMatchClassMethodPointcut;
import proxyfactorybean.TransactionAdvice;
import service.UserService;
import service.UserServiceImpl;

@Configuration
public class DaoFactory {

    @Bean
    public UserDao userDao() {
        UserDaoJdbc userDao = new UserDaoJdbc();
        userDao.setDataSource(dataSource());
        return userDao;
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost/spring_batch");
        dataSource.setUsername("root");
        dataSource.setPassword("password");
        return dataSource;
    }

    @Bean
    public UserService userService() {
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDao(userDao());
        userService.setUserLevelUpgradePolicy(userLevelUpgradePolicy());
        userService.setDataSource(dataSource());
        userService.setTransactionManager(transactionManager());
        userService.setMailSender(dummyMailSender());
        return userService;
    }

    @Bean
    public UserLevelUpgradePolicy userLevelUpgradePolicy() {
        return new DefaultUserLevelUpgradePolicy();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public MailSender mailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("mail.server.com");
        return javaMailSender;
    }

    @Bean
    public MailSender dummyMailSender() {
        return new DummyMailSender();
    }

    @Bean
    public MessageFactoryBean message() {
        MessageFactoryBean messageFactoryBean = new MessageFactoryBean();
        messageFactoryBean.setText("Factory Bean");
        return messageFactoryBean;
    }

    @Bean
    public TransactionAdvice transactionAdvice() {
        TransactionAdvice transactionAdvice = new TransactionAdvice();
        transactionAdvice.setTransactionManager(transactionManager());
        return transactionAdvice;
    }


    @Bean
    public DefaultPointcutAdvisor transactionAdvisor() {
        return new DefaultPointcutAdvisor(transactionPointcut(), transactionAdvice());
    }


    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    @Bean
    public NameMatchClassMethodPointcut transactionPointcut() {
        NameMatchClassMethodPointcut nameMatchClassMethodPointcut = new NameMatchClassMethodPointcut();
        nameMatchClassMethodPointcut.setMappedClassName("*ServiceImpl");
        nameMatchClassMethodPointcut.setMappedName("upgrade*");
        return nameMatchClassMethodPointcut;
    }
}
