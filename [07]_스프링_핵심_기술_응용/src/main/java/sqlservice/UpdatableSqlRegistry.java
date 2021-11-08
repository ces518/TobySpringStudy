package sqlservice;

import errors.SqlUpdateFailureException;
import java.util.Map;

/**
 * SqlUpdate 기능이 필요하다고 해서 기존 인터페이스를 수정하는것은 좋은 방법이 아니다.
 * 기존인터페이스에 의존하고 있는 객체들에게 영향이 가기 때문이다.
 * -> 클라이언트의 목적과 용도에 적합한 인터페이스 만을 제공해야 한다!
 * 때문에 기존 인터페이스를 확장하는 새로운 인터페이스를 만드는 것이 더 좋은 방법이다.
 */
public interface UpdatableSqlRegistry extends SqlRegistry {

    void updateSql(String key, String sql) throws SqlUpdateFailureException;

    void updateSql(Map<String, String> sqlmap) throws SqlUpdateFailureException;
}
