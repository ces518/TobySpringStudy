package sqlservice;

import errors.SqlNotFoundException;
import errors.SqlUpdateFailureException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapSqlRegistry implements UpdatableSqlRegistry {

    /**
     * 동시성 처리를 위해 ConcurrentHashMap 사용
     */
    private Map<String, String> sqlmap = new ConcurrentHashMap<>();

    @Override
    public void registerSql(String key, String sql) {
        sqlmap.put(key, sql);
    }

    @Override
    public String findSql(String key) throws SqlNotFoundException {
        String sql = sqlmap.get(key);
        if (sql == null) {
            throw new SqlNotFoundException(key + " 에 해당하는 SQL 을 찾을 수 없습니다.");
        }
        return sql;
    }

    @Override
    public void updateSql(String key, String sql) throws SqlUpdateFailureException {
        if (!sqlmap.containsKey(key)) {
            throw new SqlUpdateFailureException(key + " 에 해당하는 SQL 을 찾을 수 없습니다.");
        }
        sqlmap.put(key, sql);
    }

    @Override
    public void updateSql(Map<String, String> sqlmap) throws SqlUpdateFailureException {
        for (Entry<String, String> entry : sqlmap.entrySet()) {
            updateSql(entry.getKey(), entry.getValue());
        }
    }
}
