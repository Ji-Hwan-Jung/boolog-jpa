package com.stoph.boolog.domain.post.repository;

import com.stoph.boolog.domain.member.Member;
import com.stoph.boolog.domain.post.Period;
import com.stoph.boolog.domain.post.Post;
import com.stoph.boolog.domain.post.Tag;
import com.stoph.boolog.web.dto.PostUpdateDto;
import com.stoph.boolog.web.dto.TagResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<Tag> tagDuplicateValidation(List<Tag> tags) {
        List<Tag> persistentTags = new ArrayList<>();
        if (!tags.isEmpty()) {
            for (Tag tag : tags) {
                List<Tag> existTag = em.createQuery("select t from Tag t where t.tagName = :tagName", Tag.class)
                        .setParameter("tagName", tag.getTagName())
                        .getResultList();

                if (existTag.isEmpty()) {
                    em.persist(tag);
                    persistentTags.add(tag);
                } else {
                    persistentTags.add(existTag.get(0));
                }
            }
        }
        return persistentTags;
    }

    @Override
    public void update(Long id, PostUpdateDto updateDto) {
        Post post = em.find(Post.class, id);
        post.updatePost(updateDto);
        em.flush();
        post.addTag(tagDuplicateValidation(updateDto.getTagList()));
    }

    @Override
    public Page<Post> findAllByKeyword(String keyword, Pageable pageable) {
        List<Post> result = em.createQuery("select p from Post p " +
                        "where p.title like concat('%', :keyword, '%')" +
                        "or p.content like concat('%', :keyword, '%')" +
                        "or p.description like concat('%', :keyword, '%')" +
                        "or p.member.name like concat('%', :keyword, '%')" +
                        "order by p.createdDate desc", Post.class)
                .setParameter("keyword", keyword)
                .getResultList();

        int start = (int)pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), result.size());

        return new PageImpl<Post>(result.subList(start, end), pageable, result.size());
    }

    @Override
    public Page<Post> findAllOrderByLikedDesc(Pageable pageable) {
        List<Post> result = em.createQuery("select p from Post p order by p.liked desc, p.createdDate desc", Post.class)
                .getResultList();

        int start = (int)pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), result.size());

        return new PageImpl<Post>(result.subList(start, end), pageable, result.size());
    }

    @Override
    public Page<Post> findAllByPeriodOrderByLikedDesc(Period period, Pageable pageable) {

        List<Post> result = new ArrayList<>();

        if (period.equals(Period.daily)) {
            result = em.createQuery("select p from Post p " +
                            "where function('datediff', hour, p.createdDate, current_timestamp) between 0 and 23", Post.class)
                    .getResultList();
        } else if (period.equals(Period.weekly)) {
            result = em.createQuery("select p from Post p " +
                            "where function('datediff', day, p.createdDate, current_timestamp) between 0 and 6", Post.class)
                    .getResultList();
        } else if (period.equals(Period.monthly)) {
            result = em.createQuery("select p from Post p " +
                            "where function('datediff', month, p.createdDate, current_timestamp) between 0 and 1", Post.class)
                    .getResultList();
        } else if (period.equals(Period.yearly)) {
            result = em.createQuery("select p from Post p " +
                            "where function('datediff', year, p.createdDate, current_timestamp) between 0 and 1", Post.class)
                    .getResultList();
        } else {
            result = em.createQuery("select p from Post p order by p.liked desc, p.createdDate desc", Post.class)
                    .getResultList();
        }

        int start = (int)pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), result.size());

        return new PageImpl<Post>(result.subList(start, end), pageable, result.size());
    }

    @Override
    public Page<Post> findAllDesc(Pageable pageable) {
        List<Post> result = em.createQuery("select p from Post p order by p.createdDate desc", Post.class)
                .getResultList();

        int start = (int)pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), result.size());

        return new PageImpl<Post>(result.subList(start, end), pageable, result.size());
    }

    @Override
    public Page<Post> findAllByTag(String tag, Pageable pageable) {
        List<Post> result = em.createQuery("select pt.post from PostTag pt where pt.tag.tagName = :tagName order by pt.post.createdDate desc", Post.class)
                .setParameter("tagName", tag.toLowerCase())
                .getResultList();

        int start = (int)pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), result.size());

        return new PageImpl<Post>(result.subList(start, end), pageable, result.size());
    }

    @Override
    public List<Post> findAllByMember(Long id) {
        Member member = em.find(Member.class, id);

        return em.createQuery("select p from Post p where p.member = :member order by p.createdDate desc", Post.class)
                .setParameter("member", member)
                .getResultList();
    }


    @Override
    public Page<Post> findAllByMemberAndTag(Long id, String tag, Pageable pageable) {
        Member member = em.find(Member.class, id);
        List<Post> result = new ArrayList<>();

        if (tag.isBlank()) {
            result = em.createQuery("select p from Post p where p.member = :member order by p.createdDate desc", Post.class)
                    .setParameter("member", member)
                    .getResultList();
        } else {
            result = em.createQuery("select p from Post p join p.tags pt where p.member = :member and pt.tag.tagName = :tag order by p.createdDate desc", Post.class)
                    .setParameter("member", member)
                    .setParameter("tag", tag)
                    .getResultList();
        }

        int start = (int)pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), result.size());

        return new PageImpl<Post>(result.subList(start, end), pageable, result.size());
    }

    @Override
    public List<TagResponseDto> findCountOfTag(Long id) {
        Member member = em.find(Member.class, id);

        List<TagResponseDto> tagList = em.createQuery("select new com.stoph.boolog.web.dto.TagResponseDto(pt.tag.tagName, count(pt.tag.tagName)) from PostTag pt " +
                        "where pt.post.member = :member group by pt.tag.tagName " +
                        "order by pt.tag.tagName", TagResponseDto.class)
                .setParameter("member", member)
                .getResultList();

        return tagList;
    }

    @Override
    public Page<Post> findAllLiked(Long id, Pageable pageable) {
        Member member = em.find(Member.class, id);

        List<Post> result = em.createQuery("select l.post from LikedPost l where l.member = :member order by l.createdDate desc", Post.class)
                .setParameter("member", member)
                .getResultList();

        int start = (int)pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), result.size());

        return new PageImpl<Post>(result.subList(start, end), pageable, result.size());
    }

    @Override
    public void addLikedPost(Long memberId, Long postId) {
        Member member = em.find(Member.class, memberId);
        Post post = em.find(Post.class, postId);

        member.addLikedPost(post);
        em.flush();
        em.clear();
    }

    @Override
    public void removeLikedPost(Long memberId, Long postId) {
        Member member = em.find(Member.class, memberId);
        Post post = em.find(Post.class, postId);

        member.removeLikedPost(post);
        em.flush();
        em.clear();
    }
}
