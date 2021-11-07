package sqlservice;

import dao.UserDao;
import errors.SqlNotFoundException;
import errors.SqlRetrievalFailureException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import sqlservice.jaxb.SqlType;
import sqlservice.jaxb.Sqlmap;

public class XmlSqlService implements SqlService, SqlRegistry, SqlReader {

    private SqlReader reader;
    private SqlRegistry registry;

    private Map<String, String> sqlMap = new HashMap<>();
    private String sqlmapFile;

    public XmlSqlService() {

    }

    public void setSqlmapFile(String sqlmapFile) {
        this.sqlmapFile = sqlmapFile;
    }

    public void setReader(SqlReader reader) {
        this.reader = reader;
    }

    public void setRegistry(SqlRegistry registry) {
        this.registry = registry;
    }

    /**
     * 스프링이 제공하는 BeanPostProcessor
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

    @Override
    public void registerSql(String key, String sql) {
        sqlMap.put(key,sql);
    }

    @Override
    public String findSql(String key) throws SqlNotFoundException {
        String sql = sqlMap.get(key);
        if (sql == null) {
            throw new SqlRetrievalFailureException(key + " 에 해당하는 SQL 을 찾을 수 없습니다.");
        }
        return sql;
    }

    @Override
    public void read(SqlRegistry registry) {
        String contextPath = Sqlmap.class.getPackage().getName();
        try {
            JAXBContext context = JAXBContext.newInstance(contextPath);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            InputStream is = UserDao.class.getResourceAsStream(sqlmapFile);
            Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(is);
            for (SqlType sql : sqlmap.getSql()) {
                registry.registerSql(sql.getKey(), sql.getValue());
            }
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
