package com.stoph.boolog;

import com.stoph.boolog.domain.member.repository.MemberRepository;
import com.stoph.boolog.domain.post.repository.PostRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityManager;

@SpringBootApplication
public class BoologApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoologApplication.class, args);
	}

	@Profile("local")
	@Bean
	public TestDataInit testDataInit(PostRepository postRepository, MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder, EntityManager em) {
		return new TestDataInit(postRepository, memberRepository, passwordEncoder, em);
	}

}
