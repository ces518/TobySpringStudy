package sqlservice;

import errors.SqlNotFoundException;
import errors.SqlUpdateFailureException;
import java.util.Map;
import java.util.Map.Entry;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

public class EmbeddedDbSqlRegistry implements UpdatableSqlRegistry {

    JdbcTemplate template;
    TransactionTemplate transactionTemplate;

    public void setDataSource(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
        transactionTemplate = new TransactionTemplate(
            new DataSourceTransactionManager(dataSource)
        );
    }

    @Override
    public void registerSql(String key, String sql) {
        template.update("insert into sqlmap(key_, sql_) values(?, ?)", key, sql);
    }

    @Override
    public String findSql(String key) throws SqlNotFoundException {
        try {
            return template.queryForObject(
                "select sql_ from sqlmap where key_ = ?",
                String.class,
                key
            );
        } catch (EmptyResultDataAccessException e) {
            throw new SqlNotFoundException(key + " 에 해당하는 SQL 을 찾을 수 없습니다.", e);
        }
    }

    @Override
    public void updateSql(String key, String sql) throws SqlUpdateFailureException {
        int affected = template.update("update sqlmap set sql_ = ? where key_ = ?", sql, key);
        if (affected == 0) {
            throw new SqlUpdateFailureException(key + " 에 해당하는 SQL 을 찾을 수 없습니다.");
        }
    }

    /**
     * 일반적으로는 트랜잭션 관련 객체를 싱글톤 빈으로 등록해야 사용한다.
     * 하지만 적용범위가 EmbeddedDbSqlRegistry 처럼 협소한경우 TransactionTemplate 을 사용하는 방식도 있다.
     * 또한 특정 메소드에서만 적용할 것이기 때문에 TransactionTemplate 을 직접 생성해서 사용하는 것이 낫다.
     */
    @Override
    public void updateSql(Map<String, String> sqlmap) throws SqlUpdateFailureException {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                for (Entry<String, String> entry : sqlmap.entrySet()) {
                    updateSql(entry.getKey(), entry.getValue());
                }
            }
        });
    }
}
