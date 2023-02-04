package com.stoph.boolog.config.security;

import com.stoph.boolog.config.security.auth.LoginSuccessHandler;
import com.stoph.boolog.config.security.oauth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable().headers().frameOptions().disable();

        http.authorizeRequests()
                .mvcMatchers("/setting/withdrawal").hasRole("USER") // => 조건이 맞지 않으면 AccessDeniedException 발생
                .mvcMatchers("/post/write", "/post/{id}/*").authenticated() // => 조건이 맞지 않으면 AuthenticationException 발생
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
                .deleteCookies("SESSION")
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
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);

        return http.build();
    }
}
