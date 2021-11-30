package me.june.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;
import me.june.Member;
import org.springframework.beans.factory.annotation.Autowired;

public class JpaMemberDao {

//    JpaTemplate jpaTemplate;

//    @Autowired
    @PersistenceUnit // @Autowired 대신 JPA 표준 방식 사용 / 의존성 주입을 동일하게 받을 수 있고, 스프링 환경이 아니어도 사용이 가능하다.
    // PersistenceAnnotationBeanPostProcessor 가 빈으로 등록되어 있어야 한다.
    EntityManagerFactory emf;

    // 기본적으로 TRANSACITON 스코프를 갖는다. 때문에 트랜잭션 지원을 받을 수 있음. (기본값)
    @PersistenceContext(type = PersistenceContextType.EXTENDED) // 이는 상태유지 세션 빈이 된다. 사용자 별로 독립적으로 만들어진다. 싱글톤 빈에서는 사용할 수 없다.
    EntityManager em; // EntityManager 는 Thread-Safe 하지 않다.
    // @PersistenceContext 로 주입받는 경우 Proxy 로 주입되고, 매 요청마다 다른 EntityManager 를 팩토리를 통해 가져온다. 때문에 Thread-Safe 하게 됨

    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
//        jpaTemplate = new JpaTemplate(emf);
    }

    public void addMember(Member member) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(member);

        entityManager.getTransaction().commit();
    }

}
