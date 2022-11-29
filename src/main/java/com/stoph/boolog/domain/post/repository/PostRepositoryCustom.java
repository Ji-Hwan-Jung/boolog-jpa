package com.stoph.boolog.domain.post.repository;

import com.stoph.boolog.domain.post.Period;
import com.stoph.boolog.domain.post.Post;
import com.stoph.boolog.domain.post.Tag;
import com.stoph.boolog.web.dto.PostUpdateDto;
import com.stoph.boolog.web.dto.TagResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {

    List<Tag> tagDuplicateValidation(List<Tag> tags);

    void update(Long id, PostUpdateDto updateDto);

    Page<Post> findAllByKeyword(String keyword, Pageable pageable);

    Page<Post> findAllOrderByLikedDesc(Pageable pageable);

    Page<Post> findAllByPeriodOrderByLikedDesc(Period period, Pageable pageable);

    Page<Post> findAllDesc(Pageable pageable);

    Page<Post> findAllByTag(String tag, Pageable pageable);

    List<Post> findAllByMember(Long id);
    Page<Post> findAllByMemberAndTag(Long id, String tag, Pageable pageable);

    List<TagResponseDto> findCountOfTag(Long id);

    Page<Post> findAllLiked(Long id, Pageable pageable);

    void addLikedPost(Long memberId, Long postId);

    void removeLikedPost(Long memberId, Long postId);
}
