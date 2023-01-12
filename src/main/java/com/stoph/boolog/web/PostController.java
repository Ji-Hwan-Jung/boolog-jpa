package com.stoph.boolog.web;

import com.stoph.boolog.config.LoginMember;
import com.stoph.boolog.config.dto.SessionMember;
import com.stoph.boolog.domain.post.Period;
import com.stoph.boolog.domain.post.Post;
import com.stoph.boolog.utils.WebUtils;
import com.stoph.boolog.service.MemberService;
import com.stoph.boolog.service.PostService;
import com.stoph.boolog.web.dto.PostResponseDto;
import com.stoph.boolog.web.dto.PostRequestDto;
import com.stoph.boolog.web.dto.PostUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.stoph.boolog.utils.WebUtils.*;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "http://chiye1890.dothome.co.kr", methods = RequestMethod.GET)
@Controller
public class PostController {

    private final PostService postService;
    private final MemberService memberService;


    //URL 리다이렉트
    @GetMapping("/post")
    public String mainRedirect() {
        return "redirect:/";
    }

    //인기 포스트
    @GetMapping("/post/popular")
    public String popular(@RequestParam(value = "period", defaultValue = "daily") String period,
                          @RequestParam(value = "page", defaultValue = "1") int page,
                          Model model) {

        Page<PostResponseDto> posts = postService.findAllPopularByPeriod(Period.valueOf(period), page);

        List<Integer> pageList = getPageIndexes(page, posts.getTotalPages());  // 선택된 페이지에 따른 사용자에게 보여질 페이지 인덱스 목록

        model.addAttribute("pageList", pageList);
        model.addAttribute("popularList", posts);
        model.addAttribute("currentPage", page);

        model.addAttribute("periodKey", period);
        model.addAttribute("periodValue", Period.valueOf(period).getPeriod());

        return "popular";
    }

    //최신 포스트
    @GetMapping("/post/recent")
    public String recent(@RequestParam(value = "page", defaultValue = "1") int page,
                         Model model) {

        Page<PostResponseDto> posts = postService.findAllRecent(page);

        List<Integer> pageList = WebUtils.getPageIndexes(page, posts.getTotalPages());

        model.addAttribute("pageList", pageList);
        model.addAttribute("currentPage", page);
        model.addAttribute("recentList", posts);

        return "recent";
    }

    //포스트 세부내용
    @GetMapping("/post/{id}")
    public String post(@PathVariable("id") Long postId, Model model, @LoginMember SessionMember member) {

        Post post = postService.findById(postId);

        PostResponseDto responseDto = post.toResponseDto();

        List<String> tags = post.getTags().stream()
                .map(postTag -> postTag.getTag().getTagName())
                .collect(Collectors.toList());

        model.addAttribute("post", responseDto);
        model.addAttribute("tags", tags);

        if (member == null) {
            model.addAttribute("hasAuthority", false);
            model.addAttribute("isLiked", false);  //세션이 없기 때문에 false로 설정
        } else {
            Long memberId = memberService.findByEmail(member.getEmail()).getId();
            boolean isLiked = postService.isLiked(memberId, postId);

            model.addAttribute("hasAuthority", member.getName().equals(responseDto.getAuthor()));
            model.addAttribute("isLiked", isLiked);
        }

        return "post";
    }

    //검색결과 리스트
    @GetMapping("/post/search")
    public String search(@RequestParam("q") String keyword,
                         @RequestParam(value = "page", defaultValue = "1") int page,
                         Model model) {

        Page<PostResponseDto> posts = postService.findAllByKeyword(keyword, page);

        List<Integer> pageList = WebUtils.getPageIndexes(page, posts.getTotalPages());

        model.addAttribute("pageList", pageList);
        model.addAttribute("result", posts);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);

        return "search";
    }

    @GetMapping("/post/write")
    public String write(Model model, @LoginMember SessionMember member) {
        return "write";
    }

    @ResponseBody
    @PostMapping("/post/write")
    public String addPost(@ModelAttribute PostRequestDto post, @LoginMember SessionMember member) {
        return postService.write(member.getEmail(), post).toString();
    }

    @GetMapping("/post/{id}/edit")
    public String edit(@PathVariable Long id, Model model, @LoginMember SessionMember member) {

        //세션에 있는 유저의 이름과 게시글을 작성한 유저의 이름을 비교하기 위해 조회
        PostResponseDto post = postService.findById(id).toResponseDto();

        if (member != null && (member.getName().equals(post.getAuthor()))) {
            model.addAttribute("post", post);
            return "edit";
        } else {
            return "redirect:/post/" + id;
        }
    }

    @ResponseBody
    @PutMapping("/post/{id}/edit")
    public String edit(@ModelAttribute PostUpdateDto post,
                       @PathVariable Long id,
                       @LoginMember SessionMember member) {

        //세션에 있는 유저의 이름과 게시글을 작성한 유저의 이름을 비교하기 위해 조회
        String author = postService.findById(id).toResponseDto().getAuthor();

        if (member != null && (member.getName().equals(author))) {
            postService.modify(id, post);
        }

        return id.toString();
    }

    @ResponseBody
    @DeleteMapping("/post/{id}/delete")
    public String delete(@PathVariable Long id, @LoginMember SessionMember member) {

        String author = postService.findById(id).toResponseDto().getAuthor();

        if (member != null && (member.getName().equals(author))) {
            postService.delete(id);
        }

        return "success";
    }

    @GetMapping("/post/liked")
    public String liked(Model model,
                        @RequestParam(value = "page", defaultValue = "1") int page,
                        @LoginMember SessionMember member) {

        Long memberId = memberService.findByEmail(member.getEmail()).getId();

        Page<PostResponseDto> posts = postService.findAllLiked(memberId, page);

        List<Integer> pageList = WebUtils.getPageIndexes(page, posts.getTotalPages());

        model.addAttribute("pageList", pageList);
        model.addAttribute("currentPage", page);
        model.addAttribute("likedList", posts);

        return "liked";
    }

    @ResponseBody
    @PostMapping("/post/{id}/thumb-up")
    public String thumbUp(@PathVariable Long id,
                          @LoginMember SessionMember member) {

        if (member != null) {
            postService.addLikedPost(member.getEmail(), id);
            Post findPost = postService.findById(id);

            return findPost.getLiked().toString();
        } else {
            return "error";  //클라이언트에서 error 처리를 하도록 (차후 바꿀 예정)
        }
    }

    @ResponseBody
    @DeleteMapping("/post/{id}/thumb-up-cancel")
    public String thumbUpCancel(@PathVariable Long id,
                                @LoginMember SessionMember member) {

        if (member != null) {
            postService.removeLikedPost(member.getEmail(), id);
            Post findPost = postService.findById(id);

            return findPost.getLiked().toString();
        } else {
            return "error";  //클라이언트에서 error 처리를 하도록 (차후 바꿀 예정)
        }
    }

    @GetMapping("/tags/{tag}")
    public String tags(@PathVariable("tag") String tag,
                       @RequestParam(value = "page", defaultValue = "1") int page,
                       Model model) {

        Page<PostResponseDto> posts = postService.findAllByTag(tag, page);

        List<Integer> pageList = WebUtils.getPageIndexes(page, posts.getTotalPages());

        model.addAttribute("tag", tag);
        model.addAttribute("currentPage", page);
        model.addAttribute("postList", posts);
        model.addAttribute("pageList", pageList);

        return "tags";
    }
}
