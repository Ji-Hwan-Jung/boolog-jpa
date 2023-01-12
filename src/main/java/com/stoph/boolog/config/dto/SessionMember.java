package com.stoph.boolog.config.dto;

import com.stoph.boolog.domain.member.Member;
import com.stoph.boolog.domain.member.MemberVO;
import com.stoph.boolog.domain.member.Role;
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
    private Role role;
    private LocalDateTime regDate;

    public SessionMember(Member member) {
        this.email = member.getEmail();
        this.name = member.getName();
        this.picture = member.getPicture();
        this.introduction = member.getIntroduction();
        this.role = member.getRole();
        this.regDate = member.getCreatedDate();
    }

    public SessionMember(MemberVO memberVO) {
        this.email = memberVO.getEmail();
        this.name = memberVO.getName();
        this.picture = memberVO.getPicture();
        this.introduction = memberVO.getIntroduction();
        this.role = memberVO.getRole();
        this.regDate = memberVO.getCreatedDate();
    }

    public void updateSession(Member member) {
        this.name = member.getName();
        this.picture = member.getPicture();
        this.introduction = member.getIntroduction();
    }
}
