package com.stoph.boolog.domain.member.repository;

import com.stoph.boolog.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    Optional<Member> findByName(String name);

    Optional<Member> findByEmail(String email);
}
