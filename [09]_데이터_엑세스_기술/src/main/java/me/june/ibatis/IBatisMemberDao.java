package me.june.ibatis;

import com.ibatis.sqlmap.client.SqlMapClient;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

public class IBatisMemberDao {

    SqlMapClientTemplate sqlMapClientTemplate;

    public void setSqlMapClientTemplate(SqlMapClient sqlMapClient) {
        this.sqlMapClientTemplate = new SqlMapClientTemplate(sqlMapClient);
    }
}
