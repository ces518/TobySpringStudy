package sqlservice;

import errors.SqlNotFoundException;
import errors.SqlRetrievalFailureException;
import javax.annotation.PostConstruct;

public class BaseSqlService implements SqlService {

    protected SqlReader reader;
    protected SqlRegistry registry;

    public BaseSqlService() {

    }


    public void setReader(SqlReader reader) {
        this.reader = reader;
    }

    public void setRegistry(SqlRegistry registry) {
        this.registry = registry;
    }

    /**
     * 스프링이 제공하는 BeanPostProcessor
     *
     * @see InitDestroyAnnotationBeanPostProcessor
     * @see CommonAnnotationBeanPostProcessor
     */
    @PostConstruct
    public void loadSql() {
        reader.read(registry);
    }

    @Override
    public String getSql(String key) throws SqlRetrievalFailureException {
        try {
            return registry.findSql(key);
        } catch (SqlNotFoundException e) {
            throw new SqlRetrievalFailureException(e);
        }
    }
}
