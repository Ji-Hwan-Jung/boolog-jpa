package com.stoph.boolog.web.dto;

import com.stoph.boolog.domain.post.Tag;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.stoph.boolog.web.utils.PostUtils.*;

@Getter
@ToString
public class PostUpdateDto {

    private String description;
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    private String tags;

    @Builder
    public PostUpdateDto(String description, String title, String content, String tags) {
        this.description = description != null ? XssValidation(description.trim()) : null;
        this.title = XssValidation(title.trim());
        this.content = XssValidation(content.trim());
        this.tags = !tags.isBlank() ? XssValidation(tags.trim()) : "";
    }

    public List<Tag> getTagList() {
        if (!this.tags.isBlank()) {
            return Stream.of(this.tags.split(","))
                    .filter(s -> s.length() > 0)
                    .map(s -> Tag.builder().tagName(s.toLowerCase()).build())
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}
