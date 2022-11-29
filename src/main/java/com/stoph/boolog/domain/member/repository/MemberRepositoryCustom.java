package com.stoph.boolog.domain.member.repository;

import com.stoph.boolog.web.dto.MemberUpdateDto;

public interface MemberRepositoryCustom {
    void update(Long id, MemberUpdateDto updateDto);
}
