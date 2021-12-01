package me.june.jpa;

import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.EntityManagerFactoryAccessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;

@Configuration
public class JpaConfig {

    /**
     * JavaSE 기동 방식응 이용한 엔티티매니저 팩토리 생성
     * PersistentProvider 자동 감지 기능을 이용해 프로바이더를 찾는다.
     * META-INF/persistence.xml 에 담긴 정보를 활용함
     * 스프링에 적용하기엔 제약사항이 많다. (DataSource 활용이 불가능함)
     * 스프링이 제공하는 바이트코드 위빙 기법도 사용할 수 없음 스프링에서 제어가 불가능하다.
     * 비추하는 방법..
     */
    @Bean
    public LocalEntityManagerFactoryBean localEntityManager() {
        return new LocalEntityManagerFactoryBean();
    }

    /**
     * 스프링이 직접 제공하는 컨테이너 관리를 위한 방식
     * JavaEE 서버에 배치하지 않아도 JPA 활용이 가능하다.
     * 스프링이 제공하는 다양한 추상화 기법 과 JPA 확장 기능등 다양한 지원을 받을 수 있기 때문에 추천하는 방법
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource());
        localContainerEntityManagerFactoryBean.setPersistenceUnitName("default 가 아니라면 Unit 네임 지정");
        localContainerEntityManagerFactoryBean.setPersistenceXmlLocation("기본 경로 및 파일이 아니라면 별도 지정필요");
        localContainerEntityManagerFactoryBean.setPackagesToScan("me.june"); // persistence.xml 없이 패키지 기반 스캐닝을 할 수 잇게 지원한다.

        // EntityManagerFactory 를 위한 프로퍼티 지정시 사용한다.
        Properties properties = new Properties();
//        properties.put("eclipselink.weaving", false);
        localContainerEntityManagerFactoryBean.setJpaProperties(properties);
//        localContainerEntityManagerFactoryBean.setJpaPropertyMap();
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        localContainerEntityManagerFactoryBean.setLoadTimeWeaver(loadTimeWeaver());
        return localContainerEntityManagerFactoryBean;
    }

    /**
     * JPA 벤더별로 다른 프로퍼티가 존재함.
     * 추상화된 벤더별로 어뎁터를 제공한다.
     * JPA 각 벤더별로 다르게 지정되어 있다.
     */
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        EclipseLinkJpaVendorAdapter eclipseLinkJpaVendorAdapter = new EclipseLinkJpaVendorAdapter();
        eclipseLinkJpaVendorAdapter.setShowSql(true);
        return eclipseLinkJpaVendorAdapter;
    }

    /**
     * 스프링이 제공하는 LoadTimeWeaver 를 사용 (javaagent 방식)
     * 각 벤더가 제공하는 javaagent 는 모든 클래스를 대상으로 확인하기때문에 성능상 문제가 많다.
     * 스프링은 JPA 벤더에 종속되지 않는 로드타임 위버를 제공한다.
     * 로드타임위버 : 런타임시에 클래스 바이트코드를 메모리에 로드하며 다이나믹하게 바이트코드를 변경해 기능을 추가하는 방식
     * 로드타임 위빙 / 로드타임 위버 라고 부른다.
     */
    @Bean
    public InstrumentationLoadTimeWeaver loadTimeWeaver() {
        return new InstrumentationLoadTimeWeaver();
    }

    /**
     * 컨테이너가 관리하는 EntityManager 는 반드시 트랜잭션 매니저가 필요하다.
     * JDBC 는 자체적으로 트랜잭션 모드를 갖고 있지만, JPA 는 반드시 트랜잭션 안에서 동작해야한다.
     * JpaTransactionManager 는 DataSourceTransactionManager 가 제공하는 DataSource 레벨의 트랜잭션 관리 기능을 동시에 제공한다.
     * 즉 JDBC 와 JPA 를 아우르는 트랜잭션 매니저 할 수 있다.
     */
    @Bean
    public JpaTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
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
