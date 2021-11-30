package me.june.ibatis;

import com.ibatis.sqlmap.client.SqlMapClient;
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
}
