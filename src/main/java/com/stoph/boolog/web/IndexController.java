package com.stoph.boolog.web;

import com.stoph.boolog.domain.post.Period;
import com.stoph.boolog.service.PostService;
import com.stoph.boolog.web.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@CrossOrigin(origins = "http://chiye1890.dothome.co.kr", methods = RequestMethod.GET)
@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostService postService;

    //메인화면
    @GetMapping("/")
    public String main(Model model) {

        List<PostResponseDto> popularList = postService.findAllPopularByPeriod(Period.all, 1)
                .stream().limit(4L).collect(Collectors.toList());
        List<PostResponseDto> recentList = postService.findAllRecent(1)
                .stream().limit(4L).collect(Collectors.toList());

        model.addAttribute("popularList", popularList);
        model.addAttribute("recentList", recentList);
        return "main";
    }

    @GetMapping("/signin")
    public String signin() {
        return "sign_in";
    }
}
