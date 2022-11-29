package com.stoph.boolog;

import com.stoph.boolog.domain.member.Member;
import com.stoph.boolog.domain.member.Role;
import com.stoph.boolog.domain.member.repository.MemberRepository;
import com.stoph.boolog.domain.post.Post;
import com.stoph.boolog.domain.post.Tag;
import com.stoph.boolog.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class TestDataInit {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EntityManager em;

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        log.info("Init Data");

        //회원 데이터
        Member member1 = Member.builder()
                .email("test1@gmail.com")
                .password(passwordEncoder.encode("1234"))
                .name("test1")
                .picture("http://chiye1890.dothome.co.kr/img/img1.png")
                .introduction("test1 입니다")
                .role(Role.USER)
                .provider(null)
                .providerId(null)
                .build();

        Member member2 = Member.builder()
                .email("test2@gmail.com")
                .password(passwordEncoder.encode("1234"))
                .name("test2")
                .picture(null)
                .introduction("test2 입니다")
                .role(Role.USER)
                .provider(null)
                .providerId(null)
                .build();

        Member member3 = Member.builder()
                .email("test3@gmail.com")
                .password(passwordEncoder.encode("1234"))
                .name("test3")
                .picture("http://chiye1890.dothome.co.kr/img/img1.png")
                .introduction("test3 입니다")
                .role(Role.USER)
                .provider(null)
                .providerId(null)
                .build();

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        //게시글 데이터
        Post post1 = Post.builder()
                .member(member1)
                .thumbnail("http://chiye1890.dothome.co.kr/img/Spring_Logo.svg")
                .title("제목 1")
                .content("내용 1")
                .build();

        Post post2 = Post.builder()
                .member(member2)
                .thumbnail("http://chiye1890.dothome.co.kr/img/Spring_Logo.svg")
                .description("인간이 군영과 힘차게 있다. 긴지라 끝까지 따뜻한 그들은 청춘의 돋고, 뿐이다. ")
                .title("나는 둘 불러 너무나 마디씩 애기 때 노루, 위에도 까닭입니다. 시와 별 아이들의 아름다운 멀듯이, 까닭이요, 슬퍼하는 계절이 위에 거외다.")
                .content("청춘의 그들의 품으며, 심장의 피고, 이상, 보라. " +
                        "앞이 피고, 일월과 품고 몸이 때문이다. " +
                        "수 무엇을 피어나는 생의 만물은 열락의 약동하다. " +
                        "인간이 군영과 힘차게 있다. 긴지라 끝까지 따뜻한 그들은 청춘의 돋고, 뿐이다. " +
                        "그들은 대고, 봄바람을 희망의 들어 철환하였는가?")
                .build();

        Post post3 = Post.builder()
                .member(member3)
                .thumbnail("http://chiye1890.dothome.co.kr/img/Spring_Logo.svg")
                .description("가슴에 용기가 쓸쓸한 찾아다녀도, 끓는다. 사랑의 가치를 심장의 싶이 두손을 관현악이며, 일월과 것은 같은 아름다우냐?")
                .title("아름다운 별을 벌써 노새, 아직 있습니다. 밤을 별 벌레는 거외다. 내 북간도에 아직 있습니다. 가득 하나에 때 밤을 토끼, 소학교 없이 지나고 있습니다.")
                .content("가슴에 용기가 쓸쓸한 찾아다녀도, 끓는다. 사랑의 가치를 심장의 싶이 두손을 관현악이며, 일월과 것은 같은 아름다우냐? 이상을 스며들어 그러므로 얼마나 인간의 날카로우나 위하여서, 것이다. 웅대한 많이 청춘의 그들을 능히 동산에는 얼음에 두기 황금시대다. 산야에 위하여, 꽃이 있는 불러 크고 모래뿐일 때까지 그리하였는가? 인간의 인생을 얼마나 못할 무엇을 것이다. 평화스러운 같지 피가 황금시대의 천하를 듣기만 때문이다.")
                .build();

        Post post4 = Post.builder()
                .member(member1)
                .thumbnail("http://chiye1890.dothome.co.kr/img/Spring_Logo.svg")
                .description("내용 2")
                .title("제목 2")
                .content("내용 2")
                .build();

        Post post5 = Post.builder()
                .member(member2)
                .thumbnail(null)
                .description("아니한 군영과 이것을 귀는 끓는 힘있다. 전인 청춘의 이상의 찾아다녀도, 스며들어 평화스러운 약동하다.")
                .title("사람은 끓는 방황하여도, 생의 구하지 이상 남는 청춘의 것이다.")
                .content("아니한 군영과 이것을 귀는 끓는 힘있다. 전인 청춘의 이상의 찾아다녀도, 스며들어 평화스러운 약동하다. 이상을 품에 같으며, 풍부하게 것이다. 전인 남는 청춘은 그들의 웅대한 이성은 커다란 철환하였는가? 크고 뭇 이것을 황금시대의 새가 석가는 얼마나 우리 이것이다.")
                .build();

        Post post6 = Post.builder()
                .member(member3)
                .thumbnail(null)
                .description("것이 동력은 소리다.이것은 그들의 운다. 바로 뛰노는 그들은 없으면, 옷을 용기가 있으랴? 품에 곳이 목숨이 수 힘있다.")
                .title("꽃 무엇을 남는 뜨거운지라, 가는 그들을 그리하였는가?")
                .content("것이 동력은 소리다.이것은 그들의 운다. 바로 뛰노는 그들은 없으면, 옷을 용기가 있으랴? 품에 곳이 목숨이 수 힘있다. 가치를 하였으며, 있는 이상의 피어나는 봄바람이다. 많이 이상은 낙원을 살았으며, 불어 보배를 얼마나 그것을 그리하였는가?")
                .build();

        Post post7 = Post.builder()
                .member(member1)
                .thumbnail(null)
                .description("내용 3")
                .title("제목 3")
                .content("내용 3")
                .build();

        Post post8 = Post.builder()
                .member(member2)
                .thumbnail(null)
                .description("아니더면, 방지하는 아름답고 보이는 보는 과실이 말이다. 바이며, 따뜻한 놀이 보라.")
                .title("군영과 귀는 이상의 쓸쓸하랴?")
                .content("아니더면, 방지하는 아름답고 보이는 보는 과실이 말이다. 바이며, 따뜻한 놀이 보라. 보내는 풀밭에 방지하는 피고, 대중을 그들은 가진 뿐이다. 옷을 소리다.이것은 두기 군영과 이것이다. 이상의 봄날의 품었기 끓는다. 않는 밝은 없는 끓는 용감하고 봄바람이다.")
                .build();

        Post post9 = Post.builder()
                .member(member3)
                .thumbnail(null)
                .description("대한 그들은 예가 얼마나 예수는 듣기만 불어 안고, 것이다. 싶이 않는 곳이 품었기 찬미를 반짝이는 있는 풀밭에 것이다.")
                .title("능히 청춘에서만 위하여 청춘의 그들에게 이것이다.")
                .content("대한 그들은 예가 얼마나 예수는 듣기만 불어 안고, 것이다. 싶이 않는 곳이 품었기 찬미를 반짝이는 있는 풀밭에 것이다. 싸인 자신과 이성은 황금시대다. 있을 때까지 지혜는 그와 내려온 우리의 낙원을 황금시대다. 보는 무엇이 뭇 소금이라 쓸쓸하랴? 용감하고 같으며, 위하여, 하는 불어 청춘의 뿐이다.")
                .build();

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        postRepository.save(post4);
        postRepository.save(post5);
        postRepository.save(post6);
        postRepository.save(post7);
        postRepository.save(post8);
        postRepository.save(post9);

        Tag tag1 = Tag.builder()
                .tagName("java")
                .build();

        Tag tag2 = Tag.builder()
                .tagName("spring")
                .build();

        em.persist(tag1);
        em.persist(tag2);

        List<Tag> tags = new ArrayList<>();
        tags.add(tag1);
        tags.add(tag2);

        post1.addTag(tags);

        member1.addLikedPost(post2);
    }
}
