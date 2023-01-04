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

    @Override
    public void update(Long id, MemberUpdateDto updateDto) {
        Member findMember = em.find(Member.class, id);
        findMember.updateMember(updateDto);
    }
}
