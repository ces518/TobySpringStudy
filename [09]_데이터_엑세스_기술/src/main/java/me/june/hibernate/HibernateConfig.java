package me.june.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

import javax.sql.DataSource;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.DIALECT;
import static org.hibernate.cfg.AvailableSettings.SHOW_SQL;

@Configuration
public class HibernateConfig {

    @Autowired
    ResourceLoader resourceLoader;

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        // LocalSessionFactoryBuilder 를 활용한 SessionFactory 등록
        SessionFactory aTrue = new LocalSessionFactoryBuilder(dataSource())
                .scanPackages("me.june.hibernate")
                .setProperty(SHOW_SQL, "true")
                .setProperty(DIALECT, "..")
                .buildSessionFactory();

        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource());
        localSessionFactoryBean.setConfigLocation(resourceLoader.getResource("classpath:hibernate/hibernate.cfg.xml"));
        localSessionFactoryBean.setMappingResources("classpath:hibernate/Member.hbm.xml");
        localSessionFactoryBean.setAnnotatedClasses(Member.class); // 매핑 애노테이션 이 적용된 클래스를 지정
        localSessionFactoryBean.setPackagesToScan("me.june.hibernate"); // @Entity 애노테이션이 적용된 클래스를 스캔
        return localSessionFactoryBean;
    }

    /**
     * HibernateTransaction 은 HibernateTransactionManager / JtaTransactionManager(글로벌 트랜잭션) 두 가지를 사용할 수 있다.
     * JpaTransactionManager 와 마찬가지로 DataSourceTransactionManager 기능을 제공한다.
     */
    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
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
}
