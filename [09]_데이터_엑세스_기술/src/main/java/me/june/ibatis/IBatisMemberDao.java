package me.june.ibatis;

import com.ibatis.sqlmap.client.SqlMapClient;
import me.june.Member;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class IBatisMemberDao extends SqlMapClientDaoSupport {

    public void insert(Member member) {
        getSqlMapClientTemplate().insert("insertMember", member);
    }

}
