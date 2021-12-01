package me.june.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * Hibernate 의 Session 이 핵심 API
 * SessionFactory 로 부터 Session 을 가져올 수 있고, 일반적으로 트랜잭션과 동일한 스코프를 갖고 있다.
 */
@Repository
public class HibernateMemberDao extends HibernateDaoSupport {

    HibernateTemplate template;

    SessionFactory sessionFactory;

    public void addMember(Member member) {
        template.save(member);
        getHibernateTemplate().save(member);

        // 현재 트랜잭션에 연결되어 있는 하이버네이트 세션을 반환해준다.
        // 스프링 트랜잭션 매니저 / Jta 트랜잭션 매니저에 연동된 세션을 가져올 수 있다.
        // HibernateTemplate 은 단독실행시 트랜잭션을 자동으로 만들어주지만 이는 그러지 못한다.
        // 반드시 트랜잭션을 시작한 후에 사용해야 한다. 그렇지않으면 현재 스레드에 바인딩된 Session 이 없다는 하이버네이트 예외가 발생하게 된다.
        getSessionFactory().getCurrentSession().save(member);
    }
}
