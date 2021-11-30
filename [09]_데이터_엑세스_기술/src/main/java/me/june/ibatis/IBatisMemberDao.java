package me.june.ibatis;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

public class IBatisMemberDao {

    SqlMapClientTemplate sqlMapClientTemplate;

    public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
        this.sqlMapClientTemplate = sqlMapClientTemplate;
    }
}
