package com.stoph.boolog.config.auth;

import com.stoph.boolog.config.dto.SessionMember;
import com.stoph.boolog.domain.member.MemberVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        MemberVO member = principalDetails.getMember();

        HttpSession session = request.getSession();
        session.setAttribute("member", new SessionMember(member));

        response.sendRedirect("/");
    }
}
