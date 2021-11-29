package me.june.jdbc;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class MemberDaoExample extends JdbcDaoSupport {

    // JdbcTemplate 은 빈으로 등록가능함에도 등록하지 않아 코드 중복이 발생하고 있다.
    // 이런 경우 슈퍼클래스로 올리고, 중복을 제거할 수 있다. 스프링은 이미 JdbcDaoSupport 클래스를 제공하고 있다.
//    JdbcTemplate jdbcTemplate;
    SimpleJdbcInsert jdbcInsert;
    SimpleJdbcCall jdbcCall;

    /**
     * JdbcTemplate/DataSource 는 싱글톤으로 공유가 가능하지만, JdbcInsert/JdbcCall 은 DAO 마다 생성해서 사용하기 때문에 빈으로 등록하기 적합하지 않다.
     * 일반적으로 DataSource 만 빈으로 등록하고, Spring Jdbc 객체들은 DAO 에서 생성하는 방식을 사용한다.
     * DAO 는 DataSource 에만 의존하게 되는 구조이다.
     */
    @Autowired
    public void init(DataSource dataSource) {
        setDataSource(dataSource);
//        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("member");
        this.jdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("memberProcedure");
    }
}
