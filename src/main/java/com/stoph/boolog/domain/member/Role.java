package com.stoph.boolog.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER("ROLE_USER", "회원"), TEST("ROLE_TEST", "테스트");

    private final String key;
    private final String title;
}
