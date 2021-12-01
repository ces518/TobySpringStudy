package me.june.transaction;

import me.june.Member;

import java.util.List;

public interface MemberDao {

    void add(Member m);

    void add(List<Member> members);

    void deleteAll();

    long count();
}
