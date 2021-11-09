package config;

import dao.UserDao;
import domain.DefaultUserLevelUpgradePolicy;
import domain.UserLevelUpgradePolicy;
import java.sql.Driver;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "dao")
@Import({SqlServiceContext.class, ProductionAppContext.class})
@PropertySource("classpath:database.properties")
public class AppContext {

    @Value("${db.driverClass}")
    Class<? extends Driver> driverClass;

    @Value("${db.url}")
    String url;

    @Value("${db.username}")
    String username;

    @Value("${db.password}")
    String password;

    @Autowired
    UserDao userDao;

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public UserLevelUpgradePolicy userLevelUpgradePolicy() {
        return new DefaultUserLevelUpgradePolicy();
    }

    /**
     * 반드시 static 메소드로 선언해야함
     * BeanFactoryPostProcessor 타입의 빈은 반드시 static 이여야 한다..
     * https://mangkyu.tistory.com/177
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
