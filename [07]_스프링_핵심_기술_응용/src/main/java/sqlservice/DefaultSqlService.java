package sqlservice;

public class DefaultSqlService extends BaseSqlService {

    // DefaultSqlService 에 프로퍼티를 두고 JaxbXmlSqlReader 에 기본값을 주는것은 좋은 방식 이 아니다.
    // 이름 그대로 디폴트 의존관계일 뿐이지, 그 이상의 역할을 하게 됨.

    /**
     * 디폴트 의존관계
     */
    public DefaultSqlService() {
        setReader(new JaxbXmlSqlReader());
        setRegistry(new HashMapSqlRegistry());
    }
}
