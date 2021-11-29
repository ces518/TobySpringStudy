package me.june.jdbc;

import javax.sql.DataSource;
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
}
