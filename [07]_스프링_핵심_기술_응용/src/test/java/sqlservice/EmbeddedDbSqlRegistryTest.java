package sqlservice;

import static org.junit.jupiter.api.Assertions.fail;

import errors.SqlUpdateFailureException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class EmbeddedDbSqlRegistryTest extends AbstractUpdatableSqlRegistryTest {

    EmbeddedDatabase db;

    @Override
    protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
        db = new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.HSQL)
            .addScript("classpath:schema.sql")
            .build();

        EmbeddedDbSqlRegistry registry = new EmbeddedDbSqlRegistry();
        registry.setDataSource(db);
        return registry;
    }

    @Test
    void transactionalUpdate() {
        checkFindResult("SQL1", "SQL2", "SQL3");

        Map<String, String> sqlmap = new HashMap<>();
        sqlmap.put("KEY1", "Modified1");
        sqlmap.put("KEY99999!@#$", "Modified9999");

        try {
            registry.updateSql(sqlmap);
            fail();
        } catch (SqlUpdateFailureException ignored) {}
        checkFindResult("SQL1", "SQL2", "SQL3");
    }

    @AfterEach
    void tearDown() {
        db.shutdown();
    }
}
