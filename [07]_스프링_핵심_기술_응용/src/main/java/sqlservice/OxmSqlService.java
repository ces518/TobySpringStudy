package sqlservice;

import errors.SqlNotFoundException;
import errors.SqlRetrievalFailureException;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import org.springframework.oxm.Unmarshaller;
import sqlservice.jaxb.SqlType;
import sqlservice.jaxb.Sqlmap;

public class OxmSqlService implements SqlService {

    // 의존 오브젝트를 static 멤버로 선언하여 의존 오브젝트를 자신만이 독점하는 구조로 설계
    // 외부에선 하나의 오브젝트 지만 스스로 의존관계를 가진 오브젝트가 결합되어 동작한다.
    // 유연성은 떨어지지만 내부적으로 낮은 결합도와 높은 응집도를 가진 구현을 가진 방법..
    // -> OXM 을 이용하는 서비스 구조로 최적화 하기 위한 구조..
    private final OxmSqlReader oxmSqlReader = new OxmSqlReader();

    // OxmSqlService 는 Read/Registry 에 대한 확장 기능만 담당하고 있다.
    // 기본적인 기능은 BaseSqlService 가 처리한다. (위임 구조)
    private final BaseSqlService baseSqlService = new BaseSqlService();

    private SqlRegistry registry = new HashMapSqlRegistry();

    @PostConstruct
    public void loadSql() {
        baseSqlService.setReader(oxmSqlReader);
        baseSqlService.setRegistry(registry);
        baseSqlService.loadSql();
    }

    @Override
    public String getSql(String key) throws SqlRetrievalFailureException {
        return baseSqlService.getSql(key);
    }

    public void setRegistry(SqlRegistry registry) {
        this.registry = registry;
    }

    /**
     * OxmSqlService 를 통해 간접적으로 OxmSqlReader 가 주입받는 구조
     */

    public void setUnmarshaller(Unmarshaller unmarshaller) {
        oxmSqlReader.setUnmarshaller(unmarshaller);
    }

    public void setSqlmapFile(String sqlmapFile) {
        oxmSqlReader.setSqlmapFile(sqlmapFile);
    }

    private class OxmSqlReader implements SqlReader {

        private Unmarshaller unmarshaller;
        private String sqlmapFile;

        @Override
        public void read(SqlRegistry registry) {
            try {
                Source source = new StreamSource(sqlmapFile);
                Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(source);
                for (SqlType sql : sqlmap.getSql()) {
                    registry.registerSql(sql.getKey(), sql.getValue());
                }
            } catch (IOException e) {
                throw new IllegalArgumentException(sqlmapFile + " 을 가져올 수 없습니다.", e);
            }
        }

        public void setUnmarshaller(Unmarshaller unmarshaller) {
            this.unmarshaller = unmarshaller;
        }

        public void setSqlmapFile(String sqlmapFile) {
            this.sqlmapFile = sqlmapFile;
        }
    }
}
