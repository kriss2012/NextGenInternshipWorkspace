package com.nextgen.week4.dao;

import com.nextgen.week4.model.Member;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object (DAO) Interface for Member entities.
 */
public interface MemberDao {
    Member save(Member member);
    boolean update(Member member);
    boolean delete(int id);
    Optional<Member> findById(int id);
    Optional<Member> findByEmail(String email);
    List<Member> findAll();
}
