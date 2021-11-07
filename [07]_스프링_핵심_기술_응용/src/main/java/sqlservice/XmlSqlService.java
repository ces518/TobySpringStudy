package sqlservice;

import dao.UserDao;
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

public class XmlSqlService implements SqlService {

    private Map<String, String> sqlMap = new HashMap<>();
    private String sqlmapFile;

    public XmlSqlService() {

    }

    public void setSqlmapFile(String sqlmapFile) {
        this.sqlmapFile = sqlmapFile;
    }

    /**
     * 스프링이 제공하는 BeanPostProcessor
     * @see InitDestroyAnnotationBeanPostProcessor
     * @see CommonAnnotationBeanPostProcessor
     */
    @PostConstruct
    public void loadSql() {
        // 생성자에 복잡한 초기화 로직을 넣는것은 좋은 방법이 아니다.
        // 별도의 메소드로 추출해 호출해 주는 방식 사용..
        String contextPath = Sqlmap.class.getPackage().getName();
        try {
            JAXBContext context = JAXBContext.newInstance(contextPath);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            InputStream is = UserDao.class.getResourceAsStream(sqlmapFile);
            Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(is);
            for (SqlType sql : sqlmap.getSql()) {
                sqlMap.put(sql.getKey(), sql.getValue());
            }
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getSql(String key) throws SqlRetrievalFailureException {
        String sql = sqlMap.get(key);
        if (sql == null) {
            throw new SqlRetrievalFailureException(key + " 에 해당하는 SQL 을 찾을 수 없습니다.");
        }
        return sql;
    }
}
