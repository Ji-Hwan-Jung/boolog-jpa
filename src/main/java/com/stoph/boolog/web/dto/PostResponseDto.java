package com.stoph.boolog.web.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

import static com.stoph.boolog.web.utils.PostUtils.*;

@EqualsAndHashCode
@ToString
@Getter
public class PostResponseDto {

    private Long id;
    private String author;
    private String thumbnail;
    private String description;
    private String title;
    private String content;
    private Integer liked;
    private String tags;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @Builder
    public PostResponseDto(Long id, String author, String thumbnail, String description, String title, String content, Integer liked, String tags, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.author = author;
        this.thumbnail = thumbnail;
        this.description = description != null ? XssValidation(description.trim()) : null;
        this.title = XssValidation(title.trim());
        this.content = XssValidation(content.trim());
        this.liked = liked;
        this.tags = XssValidation(tags.trim());
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
