package config;

import dao.UserDao;
import domain.DefaultUserLevelUpgradePolicy;
import domain.UserLevelUpgradePolicy;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import sqlservice.EmbeddedDbSqlRegistry;
import sqlservice.OxmSqlService;
import sqlservice.SqlRegistry;
import sqlservice.SqlService;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "dao")
public class AppContext {

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    UserDao userDao;

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
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public DataSource embeddedDataBase() {
        return new EmbeddedDatabaseBuilder()
            .setName("embeddedDatabase")
            .setType(EmbeddedDatabaseType.HSQL)
            .addScript(
                "classpath:schema.sql"
            )
            .build();
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

    @Bean
    public SqlRegistry sqlRegistry() {
        EmbeddedDbSqlRegistry registry = new EmbeddedDbSqlRegistry();
        registry.setDataSource(embeddedDataBase());
        return registry;
    }

    @Bean
    public UserLevelUpgradePolicy userLevelUpgradePolicy() {
        return new DefaultUserLevelUpgradePolicy();
    }
}
