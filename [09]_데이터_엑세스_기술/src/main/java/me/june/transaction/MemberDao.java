package me.june.transaction;

import me.june.Member;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MemberDao {

    void add(Member m);

    void add(List<Member> members);

    void deleteAll();

    @Transactional(readOnly = true)
    long count();
}
