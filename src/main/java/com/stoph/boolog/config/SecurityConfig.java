package com.stoph.boolog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stoph.boolog.config.auth.LoginSuccessHandler;
import com.stoph.boolog.config.oauth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable().headers().frameOptions().disable();

        http.authorizeRequests()
                .mvcMatchers("/setting/withdrawal").hasRole("USER") // => 해당 조건에 맞지 않으면 AccessDeniedException 발생
                .mvcMatchers("/post/write", "/post/{id}/*").authenticated()
                .mvcMatchers("/", "/signin", "/@*", "/post", "/post/*", "/tags/**").permitAll()
                .antMatchers("/css/**", "/image/**", "/js/**", "/h2-console/**", "/error*/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .usernameParameter("email")
                .loginPage("/signin")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .successHandler(new LoginSuccessHandler())
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .and()
                .oauth2Login()
                .loginPage("/signin")
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

        http.sessionManagement()
                .invalidSessionUrl("/signin")
                .sessionFixation().changeSessionId();

        http.exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                        if (MediaType.TEXT_HTML_VALUE.equals(request.getContentType())) {
                            response.sendRedirect("/signin");
                        } else if (MediaType.APPLICATION_JSON_VALUE.equals(request.getHeader("Accept"))) {
                            ObjectMapper objectMapper = new ObjectMapper();

                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            response.setCharacterEncoding("UTF-8");
                        }
                    }
                })
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                        String acceptHeader = request.getHeader("Accept");

                        if (MediaType.TEXT_PLAIN_VALUE.equals(acceptHeader)){
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType(MediaType.TEXT_PLAIN_VALUE);
                            response.setCharacterEncoding("UTF-8");
                            response.getWriter().write("접근 권한이 없는 요청입니다.");
                        }
                    }
                });

        return http.build();
    }
}
