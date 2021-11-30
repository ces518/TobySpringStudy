package me.june.ibatis;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.event.RowHandler;
import com.ibatis.sqlmap.engine.mapping.statement.DefaultRowHandler;
import java.util.List;
import java.util.Map;
import me.june.Member;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class IBatisMemberDao extends SqlMapClientDaoSupport {

    public void insert(Member member) {
        getSqlMapClientTemplate().insert("insertMember", member);
    }

    public void update(Member member) {
        getSqlMapClientTemplate().update("updateMember", member, 1);
        // requiredRowAffected 인자가 있는 메소드 사용시, 기대 값과 결과가 일치하지 않는다면, JdbcUpdateAffectedIncorrectNumberOfRowsException 예외가 발생한다.
    }

    public void delete(Long id) {
        getSqlMapClientTemplate().delete("deleteMember", id, 1);
        // requiredRowAffected 인자가 있는 메소드 사용시, 기대 값과 결과가 일치하지 않는다면, JdbcUpdateAffectedIncorrectNumberOfRowsException 예외가 발생한다.
    }

    public void select() {
        // 단일 결과 매핑, 3번째 인자로 전달되는 객체에 결과를 받을 수 있음
        getSqlMapClientTemplate().queryForObject("findMemberById", 1L, new Member());

        // 다중 결과 매핑
        List members = getSqlMapClientTemplate().queryForList("findAll");

        // queryForMap 은 특정 프로퍼티를 기준으로 Map 에다가 다중 로우를 담아서 반환한다, 기본키를 기준으로 잡으면, ID 값을 키로 잡은 결과가 반환됨
        Map memberMap = getSqlMapClientTemplate().queryForMap("findAll", null, "id");

        // queryWithRowHandler 는 콜백인터페이스를 통해 결과를 처리한다.
        getSqlMapClientTemplate().queryWithRowHandler("findAll", new DefaultRowHandler());
    }
}
