package com.stoph.boolog.domain.member;

import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class MemberVO implements Serializable {

    private Long id;
    private String email;
    private String password;
    private String name;
    private String picture;
    private String introduction;
    private String provider;
    private String providerId;
    private Role role;
    private LocalDateTime createdDate;

    public MemberVO(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.name = member.getName();
        this.picture = member.getPicture();
        this.introduction = member.getIntroduction();
        this.provider = member.getProvider();
        this.providerId = member.getProviderId();
        this.role = member.getRole();
        this.createdDate = member.getCreatedDate();
    }
}
