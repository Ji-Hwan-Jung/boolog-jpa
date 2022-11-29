package com.stoph.boolog.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
public class TagResponseDto {

    private String tagName;
    private Long count;

    @Builder
    public TagResponseDto(String tagName, Long count) {
        this.tagName = tagName;
        this.count = count;
    }
}
