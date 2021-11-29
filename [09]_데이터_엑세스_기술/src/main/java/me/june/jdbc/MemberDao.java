package me.june.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class MemberDao {

    private static final String MEMBER_INSERT_SQL = "INSERT INTO MEMBER(ID, NAME, POINT) VALUES (?, ?, ?)";
    private static final String MEMBER_INSERT_SQL_NAMED_PARAMETERS = "INSERT INTO MEMBER(ID, NAME, POINT) VALUES (:id, :name, :point)";

    /*
    * 파라미터 정보를 Map 을 활용해서 넣는다면, 자동으로 키값과 일치하는 값이 들어간다.
    * 코드상에서 이런 맵을 만들어야 한다면 다음과 같이 MapSqlParameterSource 를 활용하는 것도 좋다.
    * */
    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("id", 1)
        .addValue("name", "Spring")
        .addValue("point", 3.5);

    /*
    * 맵 대신 도메인객체 혹은 DTO 를 사용할 수 있다.
    * 오브젝트의 프로퍼티 명과 SQL 네임드 파라미터를 일치시켜 바인딩 해주는 방식이다.
    * 도메인 오브젝트의 프로퍼티와 SQL 의 네임드 파라미터를 일치하게만 해준다면 편리하게 사용할 수 있다.
    * */
    BeanPropertySqlParameterSource beanPropertyParams = new BeanPropertySqlParameterSource(new Member(1L, "Spring", 3.5));

    /*
    JdbcTemplate 은 Thread-Safe 하기 때문에 빈으로 등록해서 사용이 가능함.
    상태를 갖지 않고, 메소드 내에서 리소스의 생성 및 정리 작업이 이뤄진다.
    * */
    JdbcTemplate template;

    public void setDataSource(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    /**
     * SQL 실행시 INSERT, UPDATE, DELETE SQL 과 같은 DML 사용시에는 update 메소드를 사용한다.
     */
    public void execute() {
        // 가변인자
        template.update(MEMBER_INSERT_SQL_NAMED_PARAMETERS, 1, "Spring", 1.5);

        // 맵
        template.update(MEMBER_INSERT_SQL_NAMED_PARAMETERS, params);

        // SqlParameterSource
        template.update(MEMBER_INSERT_SQL_NAMED_PARAMETERS, beanPropertyParams);

    }

    public void select() {
        // queryForInt/queryForLong : Deprecated

        // SQL 실행결과로 하나의 Row 를 받는다면 문제가 없지만, 검색된 로우가 없다면 EmptyResultDataAccessException 이 발생한다.
        String name = template.queryForObject("select name from member where id = ?", String.class, 1L);

        // SQL 실행결과로 여러 컬럼을 가진 경우 사용한다. RowMapper 인터페이스 구현체를 인자로 받는다.
        Member member = template.queryForObject("select * from member where id = ?",
            new BeanPropertyRowMapper<>(Member.class), 1L);

        // SQL 실행 결과로 여러 컬럼을 가진 로우를 도메인 객체나 DTO 에 매핑해준다. queryForObject 와 달리 여러 로우를 처리할 수 있다.
        // 로우의 갯수가 0이어도 예외가 발생하지 않는다.
        List<Member> members = template.query("select * from member", new BeanPropertyRowMapper<Member>(Member.class));

        // queryForObject 와 같이 단일 로우 처리시 사용한다. 로우의 결과를 Map 에 담아 반호나해준다.
        Map<String, Object> resultMap = template.queryForMap("select * from member");

        // queryForMap 의 다중 로우 버전이다.
        List<Map<String, Object>> resultMaps = template.queryForList("select * from member");
    }

    public void batch() {
        // BatchPreparedStatementSetter 인터페이스 구현체를 인자로 받는다.
        // 내부적으로 JDBC 의 addBatch / executeBatch 메소드를 통해 SQL 을 한번에 처리한다.
        template.batchUpdate(MEMBER_INSERT_SQL_NAMED_PARAMETERS, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, "dd");
                /// ...
            }

            @Override
            public int getBatchSize() {
                return 10;
            }
        });
    }
}
