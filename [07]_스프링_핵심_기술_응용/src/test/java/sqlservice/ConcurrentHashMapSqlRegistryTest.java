package sqlservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import errors.SqlNotFoundException;
import errors.SqlUpdateFailureException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConcurrentHashMapSqlRegistryTest {

    UpdatableSqlRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new ConcurrentHashMapSqlRegistry();
        registry.registerSql("KEY1", "SQL1");
        registry.registerSql("KEY2", "SQL2");
        registry.registerSql("KEY3", "SQL3");
    }

    @Test
    void find() {
        checkFindResult("SQL1", "SQL2", "SQL3");
    }

    @Test
    void unknownKey() {
        assertThrows(SqlNotFoundException.class, () -> registry.findSql("SQL99999!@#$%"));
    }

    @Test
    void updateSingle() {
        registry.updateSql("KEY2", "Modified2");
        checkFindResult("SQL1", "Modified2", "SQL3");
    }

    @Test
    void updateMulti() {
        Map<String, String> sqlmap = new HashMap<>();
        sqlmap.put("KEY1", "Modified1");
        sqlmap.put("KEY3", "Modified3");

        registry.updateSql(sqlmap);
        checkFindResult("Modified1", "SQL2", "Modified3");
    }

    @Test
    void updateWithNotExistingKey() {
        assertThrows(SqlUpdateFailureException.class,
            () -> registry.updateSql("SQL99999!@#$!@%!@%", "Modified2"));
    }

    private void checkFindResult(String expected1, String expected2, String expected3) {
        assertThat(registry.findSql("KEY1")).isEqualTo(expected1);
        assertThat(registry.findSql("KEY2")).isEqualTo(expected2);
        assertThat(registry.findSql("KEY3")).isEqualTo(expected3);
    }
}