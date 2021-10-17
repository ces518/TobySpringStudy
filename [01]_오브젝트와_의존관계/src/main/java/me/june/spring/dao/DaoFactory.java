package me.june.spring.dao;

import javax.sql.DataSource;
import me.june.spring.infra.ConnectionMaker;
import me.june.spring.infra.SimpleConnectionMaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration
public class DaoFactory {

    @Bean
    public UserDao userDao() {
        UserDao userDao = new UserDao();
        userDao.setDataSource(dataSource());
        return userDao;
    }

    @Bean
    public AccountDao accountDao() {
        ConnectionMaker connectionMaker = connectionMaker();
        return new AccountDao(connectionMaker);
    }

    @Bean
    public MessageDao messageDao() {
        ConnectionMaker connectionMaker = connectionMaker();
        return new MessageDao(connectionMaker);
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

    private ConnectionMaker connectionMaker() {
        return new SimpleConnectionMaker();
    }
}
