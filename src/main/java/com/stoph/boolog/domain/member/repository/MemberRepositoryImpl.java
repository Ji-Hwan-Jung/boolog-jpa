package com.stoph.boolog.domain.member.repository;

import com.stoph.boolog.domain.member.Member;
import com.stoph.boolog.web.dto.MemberUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;

@Slf4j
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final EntityManager em;

//    public Long save(Member member) {
//        em.persist(member);
//        return member.getId();
//    }
    @Override
    public void update(Long id, MemberUpdateDto updateDto) {
        Member findMember = em.find(Member.class, id);
        findMember.updateMember(updateDto);
    }
//
//    public Member findById(Long id) {
//        return em.find(Member.class, id);
//    }
//
//    public Member findByEmail(String email) {
//        return em.createQuery("select m from Member m where m.email = :email", Member.class)
//                .setParameter("email", email)
//                .getSingleResult();
//    }
//
//    public Member findByName(String name) {
//        return em.createQuery("select m from Member m where m.name = :name", Member.class)
//                .setParameter("name", name)
//                .getSingleResult();
//    }
//
//    public void delete(Long id) {
//        Member findMember = em.find(Member.class, id);
//        em.remove(findMember);
//    }
}
