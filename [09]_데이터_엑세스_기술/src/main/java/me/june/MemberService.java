package me.june;

import me.june.jdbc.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Transactional
public class MemberService {

    @Autowired
    MemberDao memberDao;

    TransactionTemplate transactionTemplate;

    @Autowired
    public void init(PlatformTransactionManager transactionManager) {
        transactionTemplate = new TransactionTemplate(transactionManager);
    }


    public void addMembers(final List<Member> members) {
        // 트랜잭션 속성을 변경하려면 TransactionTemplate 을 생성할 때 TransactionDefinition 을 인자로 제공하면 된다.
        // 기본 속성을 사용한다면 미리 만들어두고 재사용할 수 있다.
        // 대부분의 경우 선언적 트랜잭션 방식을 사용한다. (테스트 코드의 경우 의도적으로 종료시키거나 할 때 사용)
        // TransactionStatus 를 활용해 현재 트랜잭션이 새롭게 시작되었는지 기존 트랜잭션 참여인지, 혹은 종료인지 여부를 확인할 수 있다.
        transactionTemplate.execute(transactionStatus -> {
            for (Member member : members) {
//                    memberDao.addMember(member);
            }
            return null; // 정상 작업후 반환된다면 트랜잭션이 커밋된다.
        });
    }

    /**
     * 프록시 기반이기 때문에 주의해서 사용해야 한다.
     * 반드시 "클라이언트를 통한 호출" 에서만 프록시가 적용된다.
     * 자기 자신의 메소드를 호출할 때에는 프록시가 적용되지 않는다.
     * addMember() 메소드는 REQUIRES_NEW 가 적용되지 않고, complexWork 메소드의 트랜잭션에 참여하게 될 뿐이다.
     *
     * 이런 문제를 해결하는 방식은 두 가지 방법이 있다.
     * 1. AopContext.currentProxy() 현재 프록시를 가져오게끔 제공해주는 메소드이다. Thread-Local 기반으로 동작하기 때문에 유의해서 사용해야 한다.
     * 2. AspectJ AOP : 프록시 대신 바이트코드를 직접 변경해서 부가기능을 추가하기 때문에 자신의 메소드를 호출하더라도 잘 동작한다.
     */
    public void complexWork() {
        // ..
        this.addMember(new Member());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addMember(Member member) {
        // ...
    }
}
