package com.stoph.boolog.service;

import com.stoph.boolog.domain.member.Member;
import com.stoph.boolog.domain.member.repository.MemberRepository;
import com.stoph.boolog.exception.NoSuchMemberException;
import com.stoph.boolog.web.dto.MemberUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long signUp(Member member) {
        Member findMember = memberRepository.save(member);
        return findMember.getId();
    }

    @Transactional
    public void update(Long id, MemberUpdateDto updateDto) {
        memberRepository.update(id, updateDto);
    }

    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(NoSuchMemberException::new);
    }

    public Member findByName(String name) {
        return memberRepository.findByName(name)
                .orElseThrow(NoSuchMemberException::new);
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(NoSuchMemberException::new);
    }

    @Transactional
    public void delete(Long id) {
        Member findMember = memberRepository.findById(id)
                .orElseThrow(NoSuchMemberException::new);
        memberRepository.delete(findMember);
    }
}
