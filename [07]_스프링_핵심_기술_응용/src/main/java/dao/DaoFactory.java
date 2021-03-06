package dao;

import domain.DefaultUserLevelUpgradePolicy;
import domain.UserLevelUpgradePolicy;
import factorybean.MessageFactoryBean;
import java.util.Properties;
import javax.sql.DataSource;
import mail.DummyMailSender;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import service.UserService;
import service.UserServiceImpl;
import sqlservice.DefaultSqlService;
import sqlservice.HashMapSqlRegistry;
import sqlservice.JaxbXmlSqlReader;
import sqlservice.OxmSqlService;
import sqlservice.SqlReader;
import sqlservice.SqlRegistry;
import sqlservice.SqlService;
import sqlservice.BaseSqlService;

@Configuration
public class DaoFactory {

    @Autowired
    ResourceLoader resourceLoader;

    @Bean
    public UserDao userDao() {
        UserDaoJdbc userDao = new UserDaoJdbc();
        userDao.setDataSource(dataSource());
        userDao.setSqlService(sqlService());
        return userDao;
    }

    @Bean
    public SqlService sqlService() {
        OxmSqlService sqlService = new OxmSqlService();
        sqlService.setUnmarshaller(marshaller());
        sqlService.setSqlmap(resourceLoader.getResource("classpath:dao/sqlmap.xml"));
        return sqlService;
    }

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("sqlservice.jaxb");
        return marshaller;
    }

//    @Bean
//    public SqlService sqlService() {
//        BaseSqlService sqlService = new BaseSqlService();
//        sqlService.setReader(sqlReader());
//        sqlService.setRegistry(sqlRegistry());
//        return sqlService;
//    }
//
//    @Bean
//    public SqlReader sqlReader() {
//        JaxbXmlSqlReader sqlReader = new JaxbXmlSqlReader();
//        sqlReader.setSqlmapFile("sqlmap.xml");
//        return sqlReader;
//    }
//
//    @Bean
//    public SqlRegistry sqlRegistry() {
//        return new HashMapSqlRegistry();
//    }

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

//    @Bean
//    public TransactionAdvice transactionAdvice() {
//        TransactionAdvice transactionAdvice = new TransactionAdvice();
//        transactionAdvice.setTransactionManager(transactionManager());
//        return transactionAdvice;
//    }


    @Bean
    public DefaultPointcutAdvisor transactionAdvisor() {
        return new DefaultPointcutAdvisor(transactionPointcut(), transactionAdvice());
    }


    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    @Bean
    public AspectJExpressionPointcut transactionPointcut() {
        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
        aspectJExpressionPointcut.setExpression("execution(* *..*ServiceImpl.upgrade*(..))");
        return aspectJExpressionPointcut;
    }

    @Bean
    public TransactionInterceptor transactionAdvice() {
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
        transactionInterceptor.setTransactionManager(transactionManager());
        Properties properties = new Properties();
        // ???????????? ?????? ??????, ????????? ????????? DefaultDefinition ??? ?????????.
        properties.put("get*", "PROPAGATION_REQUIRED,readOnly,timeout_30");
        properties.put("upgrade*", "PROPAGATION_REQUIRES_NEW,ISOLATION_SERIALIZABLE");
        properties.put("*","PROPAGATION_REQUIRED");
        transactionInterceptor.setTransactionAttributes(properties);
        return transactionInterceptor;
    }
}
