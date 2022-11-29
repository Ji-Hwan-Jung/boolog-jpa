package com.stoph.boolog.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@ToString
@Getter
public class MemberUpdateDto {

    @NotBlank(message = "이름은 비울 수 없습니다.")
    private String name;
    private String password;
    private String picture;
    private String introduction;

    @Builder
    public MemberUpdateDto(String name, String password, String picture, String introduction) {
        this.name = name;
        this.password = password;
        this.picture = picture;
        this.introduction = introduction;
    }
}
