package com.stoph.boolog.domain.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Period {
    daily("일간"), weekly("주간"), monthly("월간"), yearly("연간"), all("전체");

    private final String period;
}
