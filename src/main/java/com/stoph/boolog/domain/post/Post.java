package com.stoph.boolog.domain.post;

import com.stoph.boolog.domain.member.Member;
import com.stoph.boolog.web.dto.PostResponseDto;
import com.stoph.boolog.web.dto.PostUpdateDto;
import lombok.*;
import org.springframework.context.annotation.Profile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@ToString(exclude = {"member", "tags"})
@EqualsAndHashCode
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    private String thumbnail;
    private String description;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    private Integer liked;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostTag> tags = new ArrayList<>();

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    @Builder
    public Post(Member member, String thumbnail, String description, String title, String content) {
        this.member = member;
        this.thumbnail = thumbnail;
        this.description = description;
        this.title = title;
        this.content = content;
    }

    @PrePersist
    public void init() {
        // default가 안 먹어서 직접 퍼시스트 전에 넣는 방식으로 전환
        if (this.liked == null) {
            this.liked = 0;
        }
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
    }

    public void testDate(LocalDateTime crDate) {
        this.createdDate = crDate;
        this.modifiedDate = LocalDateTime.now();
    }

    @PreUpdate
    public void updateDate() {
        this.modifiedDate = LocalDateTime.now();
    }

    public void updatePost(PostUpdateDto updateDto) {
        this.title = updateDto.getTitle();
        this.content = updateDto.getContent();
        this.description = updateDto.getDescription();
        this.tags.clear();
    }

    // 좋아요 수 증가 DB트리거 로직으로 구현
    public void likedAddition(){
        this.liked += 1;
    }

    // 좋아요 수 감소 DB트리거 로직으로 구현
    public void likedSubtraction(){
        this.liked -= 1;
    }

    public void addTag(List<Tag> tags) {
        if (!tags.isEmpty()) {
            for (Tag tag : tags) {
                PostTag postTag = PostTag.builder()
                        .post(this)
                        .tag(tag)
                        .build();
                if (!this.tags.contains(postTag)) {
                    this.tags.add(postTag);
                }
            }
        }
    }

    public PostResponseDto toResponseDto() {
        String tags = "";

        if (!this.tags.isEmpty()) {
            tags = this.getTags().stream()
                    .map(pt -> pt.getTag().getTagName())
                    .collect(Collectors.joining(","));
        }

        return PostResponseDto.builder()
                .id(this.getId())
                .author(this.getMember().getName())
                .thumbnail(this.getThumbnail())
                .description(this.getDescription())
                .title(this.getTitle())
                .content(this.getContent())
                .liked(this.getLiked())
                .tags(tags)
                .createdDate(this.getCreatedDate())
                .modifiedDate(this.getModifiedDate())
                .build();
    }
}
