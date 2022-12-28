package com.stoph.boolog.domain.member;

import com.stoph.boolog.domain.post.LikedPost;
import com.stoph.boolog.domain.post.Post;
import com.stoph.boolog.web.dto.MemberResponseDto;
import com.stoph.boolog.web.dto.MemberUpdateDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString(exclude = {"posts", "likedPosts"})
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "EMAIL_UNIQUE", columnNames = {"EMAIL"}),
        @UniqueConstraint(name = "NAME_UNIQUE", columnNames = {"NAME"})})
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String picture;
    private String introduction;
    private String provider;
    private String providerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikedPost> likedPosts = new ArrayList<>();

    @Builder
    public Member(String email, String password, String name, String picture,String introduction, Role role, String provider, String providerId) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.picture = picture;
        this.introduction = introduction;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }

    @PrePersist
    private void initDate() {
        this.createdDate = LocalDateTime.now();
    }

    public void updateMember(MemberUpdateDto updateDto) {
        if (updateDto.getName() != null) {
            this.name = updateDto.getName();
        }
        if (updateDto.getIntroduction() != null) {
            this.introduction = updateDto.getIntroduction();
        }
        if (updateDto.getPassword() != null) {
            this.password = updateDto.getPassword();
        }
        if (updateDto.getPicture() != null) {
            this.picture = updateDto.getPicture();
        }
    }

    public void addLikedPost(Post post) {
        LikedPost likedPost = LikedPost.builder()
                .member(this)
                .post(post)
                .build();

        if (!this.likedPosts.contains(likedPost)) {
            this.likedPosts.add(likedPost);
            post.likedAddition();
        }
    }

    public void removeLikedPost(Post post) {
        List<LikedPost> result = this.likedPosts.stream()
                .filter(likedPost -> likedPost.getPost().equals(post))
                .collect(Collectors.toList());

        if (!result.isEmpty()) {
            LikedPost likedPost = result.get(0);
            this.likedPosts.remove(likedPost);
            post.likedSubtraction();
        }
    }

    public MemberResponseDto toResponseDto() {
        return MemberResponseDto.builder()
                .email(this.email)
                .name(this.name)
                .picture(this.picture)
                .introduction(this.introduction)
                .regDate(this.createdDate)
                .build();
    }
}
