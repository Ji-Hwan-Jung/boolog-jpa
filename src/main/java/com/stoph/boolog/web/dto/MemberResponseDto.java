package com.stoph.boolog.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@ToString
@Getter
public class MemberResponseDto {

    private String email;
    private String name;
    private String picture;
    private String introduction;
    private LocalDateTime regDate;

    @Builder
    public MemberResponseDto(String email, String name, String picture, String introduction, LocalDateTime regDate) {
        this.email = email;
        this.name = name;
        this.picture = picture;
        this.introduction = introduction;
        this.regDate = regDate;
    }
}
