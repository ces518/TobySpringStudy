package config;

import dao.UserDao;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import sqlservice.EmbeddedDbSqlRegistry;
import sqlservice.OxmSqlService;
import sqlservice.SqlRegistry;
import sqlservice.SqlService;

@Configuration
public class SqlServiceContext {

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    SqlMapConfig sqlMapConfig;

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
        sqlService.setSqlmap(sqlMapConfig.getSqlMapResource());
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
}
