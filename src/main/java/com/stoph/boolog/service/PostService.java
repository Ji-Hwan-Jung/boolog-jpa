package com.stoph.boolog.service;

import com.stoph.boolog.domain.member.Member;
import com.stoph.boolog.domain.member.repository.MemberRepository;
import com.stoph.boolog.domain.post.LikedPost;
import com.stoph.boolog.domain.post.Period;
import com.stoph.boolog.domain.post.Post;
import com.stoph.boolog.domain.post.repository.PostRepository;
import com.stoph.boolog.web.dto.PostRequestDto;
import com.stoph.boolog.web.dto.PostResponseDto;
import com.stoph.boolog.web.dto.PostUpdateDto;
import com.stoph.boolog.web.dto.TagResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long write(String email, PostRequestDto requestDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Post post = requestDto.toPost(member);
        post.addTag(postRepository.tagDuplicateValidation(requestDto.getTagList()));
        postRepository.save(post);

        return post.getId();
    }

    @Transactional
    public void modify(Long id, PostUpdateDto updateDto) {
        postRepository.update(id, updateDto);
    }

    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
    }

    public List<PostResponseDto> findAll() {
        return postRepository.findAll().stream()
                .map(Post::toResponseDto).collect(Collectors.toList());
    }

    public Page<PostResponseDto> findAllPopular(Pageable pageable) {
        return postRepository.findAllOrderByLikedDesc(pageable)
                .map(Post::toResponseDto);
    }

    public Page<PostResponseDto> findAllPopularByPeriod(Period period, Pageable pageable) {
        return postRepository.findAllByPeriodOrderByLikedDesc(period, pageable)
                .map(Post::toResponseDto);
    }

    public Page<PostResponseDto> findAllRecent(Pageable pageable) {
        return postRepository.findAllDesc(pageable)
                .map(Post::toResponseDto);
    }

    public Page<PostResponseDto> findAllByKeyword(String keyword, Pageable pageable) {
        return postRepository.findAllByKeyword(keyword.trim(), pageable)
                .map(Post::toResponseDto);
    }

    public Page<PostResponseDto> findAllByTag(String tag, Pageable pageable) {
        return postRepository.findAllByTag(tag, pageable)
                .map(Post::toResponseDto);
    }

    public List<PostResponseDto> findAllByMember(String name) {
        Member member = memberRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        return postRepository.findAllByMember(member.getId())
                .stream().map(Post::toResponseDto)
                .collect(Collectors.toList());
    }

    public Page<PostResponseDto> findAllByMemberAndTag(String name, String tag, Pageable pageable) {
        Member member = memberRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        return postRepository.findAllByMemberAndTag(member.getId(), tag, pageable)
                .map(Post::toResponseDto);
    }

    public Page<PostResponseDto> findAllLiked(Long memberId, Pageable pageable) {
        return postRepository.findAllLiked(memberId, pageable)
                .map(Post::toResponseDto);
    }

    public boolean isLiked(Long memberId, Long postId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));;

        List<Post> likedPosts = member.getLikedPosts().stream()
                .map(LikedPost::getPost)
                .collect(Collectors.toList());

        return likedPosts.contains(post);
    }

    public List<TagResponseDto> findNumbersOfTag(String name) {
        Member member = memberRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));;
        return postRepository.findCountOfTag(member.getId());
    }

    @Transactional
    public void addLikedPost(String email, Long postId) {
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        postRepository.addLikedPost(findMember.getId(), postId);
    }

    @Transactional
    public void removeLikedPost(String email, Long postId) {
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        postRepository.removeLikedPost(findMember.getId(), postId);
    }

    @Transactional
    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        postRepository.delete(post);
    }
}
