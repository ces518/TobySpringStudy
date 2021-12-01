package me.june.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

/**
 * Hibernate 의 Session 이 핵심 API
 * SessionFactory 로 부터 Session 을 가져올 수 있고, 일반적으로 트랜잭션과 동일한 스코프를 갖고 있다.
 */
public class HibernateMemberDao extends HibernateDaoSupport {

    HibernateTemplate template;

//    @Autowired
//    public void setSessionFactory(SessionFactory sessionFactory) {
//        template = new HibernateTemplate(sessionFactory);
//    }

    public void addMember(Member member) {
        template.save(member);
        getHibernateTemplate().save(member);
    }
}



