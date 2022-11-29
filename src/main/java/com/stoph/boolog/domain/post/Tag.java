package com.stoph.boolog.domain.post;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode
@Getter
@NoArgsConstructor
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String tagName;

    public LocalDateTime createdDate;

    @PrePersist
    public void initDate() {
        this.createdDate = LocalDateTime.now();
    }

    @Builder
    public Tag(String tagName) {
        this.tagName = tagName.toLowerCase();
    }
}
