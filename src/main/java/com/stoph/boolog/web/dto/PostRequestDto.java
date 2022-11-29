package com.stoph.boolog.web.dto;

import com.stoph.boolog.domain.member.Member;
import com.stoph.boolog.domain.post.Post;
import com.stoph.boolog.web.utils.PostUtils;
import com.stoph.boolog.domain.post.Tag;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class PostRequestDto {

    private String thumbnail;

    private String description;

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotEmpty(message = "내용을 입력해주세요.")
    private String content;

    private String tags;

    @Builder
    public PostRequestDto(String thumbnail, String description, String title, String content, String tags) {
        this.thumbnail = thumbnail;
        this.description = description.trim();
        this.title = title.trim();
        this.content = content.trim();
        this.tags = !tags.isBlank() ? PostUtils.tagParsing(tags) : "";
    }

    public Post toPost(Member member) {
        return Post.builder()
                .member(member)
                .thumbnail(this.thumbnail)
                .description(this.description)
                .title(this.title)
                .content(this.content)
                .build();
    }

    public List<Tag> getTagList() {
        if (!tags.isBlank()) {
            return Stream.of(this.tags.split("#"))
                    .filter(s -> s.length() > 0)
                    .map(s -> Tag.builder().tagName(s.toLowerCase()).build())
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}
