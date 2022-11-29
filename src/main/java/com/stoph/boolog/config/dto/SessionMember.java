package com.stoph.boolog.config.dto;

import com.stoph.boolog.domain.member.Member;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
public class SessionMember implements Serializable {

    private String email;
    private String name;
    private String picture;
    private String introduction;
    private LocalDateTime regDate;

    public SessionMember(Member member) {
        this.email = member.getEmail();
        this.name = member.getName();
        this.picture = member.getPicture();
        this.introduction = member.getIntroduction();
        this.regDate = member.getCreatedDate();
    }

    public void updateSession(Member member) {
        this.name = member.getName();
        this.picture = member.getPicture();
        this.introduction = member.getIntroduction();
    }
}
