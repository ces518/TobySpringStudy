package me.june.transaction;

import me.june.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

/**
 * 기본적으로 Spring AOP 를 활용한 선언전 트랜잭션은, 인터페이스를 기반으로 동작한다 (JDK 다이나믹 프록시)
 * 일반적으로는 Dao 가 아닌 Service 에 트랜잭션을 적용하는것이 자연스럽다.
 */

/**
 * @Transactional 을 활용한 선언적 트랜잭션은 인터페이스/구체 클래스에 적용이 가능하며 우선순위가 있다.
 * 실제 구현체에 가까울수록 적용 우선순위가 높다.
 */
@Transactional
public class MemberDaoImpl implements MemberDao {

    SimpleJdbcInsert insert;
    JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insert = new SimpleJdbcInsert(dataSource).withTableName("member");
    }

    @Override
    public void add(Member m) {

    }

    @Override
    public void add(List<Member> members) {

    }

    @Override
    public void deleteAll() {

    }

    @Transactional(readOnly = true)
    @Override
    public long count() {
        return 0;
    }
}
