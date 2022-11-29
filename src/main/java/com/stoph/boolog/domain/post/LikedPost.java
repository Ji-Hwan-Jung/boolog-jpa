package com.stoph.boolog.domain.post;

import com.stoph.boolog.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "LIKED_POST_UNIQUE",
        columnNames = {"MEMBER_ID", "POST_ID"})})
public class LikedPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    private LocalDateTime createdDate;

    @Builder
    public LikedPost(Member member, Post post) {
        this.member = member;
        this.post = post;
    }

    @PrePersist
    private void initDate() {
        this.createdDate = LocalDateTime.now();
    }
}
