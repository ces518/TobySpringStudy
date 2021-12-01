package me.june.ibatis;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.orm.ibatis.SqlMapClientFactoryBean;

@Configuration
public class IbatisConfig {

    @Autowired
    ResourceLoader resourceLoader;

    @Bean
    public SqlMapClientFactoryBean sqlMapClient() {
        SqlMapClientFactoryBean sqlMapClientFactoryBean = new SqlMapClientFactoryBean();
        sqlMapClientFactoryBean.setDataSource(dataSource());
        sqlMapClientFactoryBean.setConfigLocation(resourceLoader.getResource("classpath:ibatis/SqlMapConfig.xml"));
        return sqlMapClientFactoryBean;
    }

    /**
     * JDBC Connection API 를 활용해 트랜잭션을 제어한다.
     * JDBC / Ibatis 에 적용할 수 있다.
     * DataSourceTransactionManager 가 사용할 DataSource 는 getConnection() 호훛ㄹ시 마다 새로운 커넥션을 제공해야 한다.
     * threadlocal 등을 활용해 저장해 두고 사용하는 DataSource 등을 사용해선 안된다.
     */
    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    /**
     * 레거시에서 사용하려면 TransactionAwareDataSourceProxy 를 적용하거나 DataSourceUtils 를 이용 해야 한다.
     * @see TransactionAwareDataSourceProxy
     */
    @Bean
    public TransactionAwareDataSourceProxy dataSourceProxy() {
        TransactionAwareDataSourceProxy transactionAwareDataSourceProxy = new TransactionAwareDataSourceProxy();
        transactionAwareDataSourceProxy.setTargetDataSource(dataSource());
        return transactionAwareDataSourceProxy;
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
