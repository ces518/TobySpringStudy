package me.june;

import me.june.jdbc.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

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

}
