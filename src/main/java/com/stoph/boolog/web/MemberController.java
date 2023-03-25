package com.stoph.boolog.web;

import com.stoph.boolog.config.LoginMember;
import com.stoph.boolog.config.security.dto.SessionMember;
import com.stoph.boolog.domain.member.Member;
import com.stoph.boolog.exception.ExistNameException;
import com.stoph.boolog.utils.WebUtils;
import com.stoph.boolog.service.MemberService;
import com.stoph.boolog.service.PostService;
import com.stoph.boolog.web.dto.MemberResponseDto;
import com.stoph.boolog.web.dto.MemberUpdateDto;
import com.stoph.boolog.web.dto.PostResponseDto;
import com.stoph.boolog.web.dto.TagResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin(origins = "http://chiye1890.dothome.co.kr", methods = RequestMethod.GET)
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final PostService postService;
    private final MemberService memberService;
    private final HttpSession httpSession;

    @GetMapping("/@{name}")
    public String profile(Model model,
                          @PathVariable("name") String name,
                          @RequestParam(value = "page", defaultValue = "1") int page,
                          @RequestParam(value = "tag", defaultValue = "") String tag) {

        List<PostResponseDto> total = postService.findAllByMember(name);

        MemberResponseDto member = memberService.findByName(name).toResponseDto();
        List<TagResponseDto> tagsAndCount = postService.findNumbersOfTag(name);
        Page<PostResponseDto> posts = postService.findAllByMemberAndTag(name, tag, page);

        List<Integer> pageList = WebUtils.getPageIndexes(page, posts.getTotalPages());

        model.addAttribute("totalNum", total.size());
        model.addAttribute("tagParam", tag);
        model.addAttribute("member", member);
        model.addAttribute("currentPage", page);
        model.addAttribute("postList", posts);
        model.addAttribute("pageList", pageList);
        model.addAttribute("tagsAndCount", tagsAndCount);

        return "profile";
    }

    @GetMapping("/setting")
    public String setting(Model model) {
        return "setting";
    }

    @ResponseBody
    @PatchMapping("/setting")
    public ResponseEntity<String> profileUpdate(@RequestBody MemberUpdateDto updateParam, @SessionAttribute(name = "member") SessionMember sessionMember) {
        if (memberService.isExists(updateParam.getName())) {
            throw new ExistNameException();
        }

        Long memberId = memberService.findByEmail(sessionMember.getEmail()).getId();
        memberService.update(memberId, updateParam);
        sessionMember.updateSession(memberService.findById(memberId));

        return new ResponseEntity<>("회원 정보가 수정되었습니다.", HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping("/setting")
    public ResponseEntity<String> withdrawal(@LoginMember SessionMember sessionMember) {
        Long memberId = memberService.findByEmail(sessionMember.getEmail()).getId();
        memberService.delete(memberId);
        httpSession.invalidate();

        return new ResponseEntity<>("회원 탈퇴가 완료되었습니다.", HttpStatus.OK);
    }
}
