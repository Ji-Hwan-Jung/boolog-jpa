package com.stoph.boolog.service;

import com.stoph.boolog.domain.member.Member;
import com.stoph.boolog.domain.member.repository.MemberRepository;
import com.stoph.boolog.domain.post.LikedPost;
import com.stoph.boolog.domain.post.Period;
import com.stoph.boolog.domain.post.Post;
import com.stoph.boolog.domain.post.repository.PostRepository;
import com.stoph.boolog.exception.NoSuchMemberException;
import com.stoph.boolog.exception.NoSuchPostException;
import com.stoph.boolog.web.dto.PostRequestDto;
import com.stoph.boolog.web.dto.PostResponseDto;
import com.stoph.boolog.web.dto.PostUpdateDto;
import com.stoph.boolog.web.dto.TagResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.stoph.boolog.utils.WebUtils.*;

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
                .orElseThrow(NoSuchMemberException::new);
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
                .orElseThrow(NoSuchPostException::new);
    }

    public List<PostResponseDto> findAll() {
        return postRepository.findAll().stream()
                .map(Post::toResponseDto).collect(Collectors.toList());
    }

    public Page<PostResponseDto> findAllPopular(int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, NUMBERS_PER_PAGE);
        return postRepository.findAllOrderByLikedDesc(pageRequest)
                .map(Post::toResponseDto);
    }

    public Page<PostResponseDto> findAllPopularByPeriod(Period period, int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, NUMBERS_PER_PAGE);
        return postRepository.findAllByPeriodOrderByLikedDesc(period, pageRequest)
                .map(Post::toResponseDto);
    }

    public Page<PostResponseDto> findAllRecent(int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, NUMBERS_PER_PAGE);
        return postRepository.findAllDesc(pageRequest)
                .map(Post::toResponseDto);
    }

    public Page<PostResponseDto> findAllByKeyword(String keyword, int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, NUMBERS_PER_PAGE);
        return postRepository.findAllByKeyword(keyword.trim(), pageRequest)
                .map(Post::toResponseDto);
    }

    public Page<PostResponseDto> findAllByTag(String tag, int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, NUMBERS_PER_PAGE);
        return postRepository.findAllByTag(tag, pageRequest)
                .map(Post::toResponseDto);
    }

    public List<PostResponseDto> findAllByMember(String name) {
        Member member = memberRepository.findByName(name)
                .orElseThrow(NoSuchMemberException::new);
        return postRepository.findAllByMember(member.getId())
                .stream().map(Post::toResponseDto)
                .collect(Collectors.toList());
    }

    public Page<PostResponseDto> findAllByMemberAndTag(String name, String tag, int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, NUMBERS_PER_PAGE);
        Member member = memberRepository.findByName(name)
                .orElseThrow(NoSuchMemberException::new);
        return postRepository.findAllByMemberAndTag(member.getId(), tag, pageRequest)
                .map(Post::toResponseDto);
    }

    public Page<PostResponseDto> findAllLiked(Long memberId, int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, NUMBERS_PER_PAGE);
        return postRepository.findAllLiked(memberId, pageRequest)
                .map(Post::toResponseDto);
    }

    public boolean isLiked(Long memberId, Long postId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NoSuchMemberException::new);
        Post post = postRepository.findById(postId)
                .orElseThrow(NoSuchPostException::new);

        List<Post> likedPosts = member.getLikedPosts().stream()
                .map(LikedPost::getPost)
                .collect(Collectors.toList());

        return likedPosts.contains(post);
    }

    public List<TagResponseDto> findNumbersOfTag(String name) {
        Member member = memberRepository.findByName(name)
                .orElseThrow(NoSuchMemberException::new);
        return postRepository.findCountOfTag(member.getId());
    }

    @Transactional
    public void addLikedPost(String email, Long postId) {
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(NoSuchMemberException::new);

        postRepository.addLikedPost(findMember.getId(), postId);
    }

    @Transactional
    public void removeLikedPost(String email, Long postId) {
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(NoSuchMemberException::new);

        postRepository.removeLikedPost(findMember.getId(), postId);
    }

    @Transactional
    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(NoSuchPostException::new);

        postRepository.delete(post);
    }
}
