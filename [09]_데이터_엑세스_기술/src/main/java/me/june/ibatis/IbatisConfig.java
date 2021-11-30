package me.june.ibatis;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
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
