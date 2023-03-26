# Boolog
>기존에 진행하고 있던 프로젝트([Boolog-MyBatis](https://github.com/Ji-Hwan-Jung/boolog-mybatis))를 JPA로 전환한 프로젝트입니다.
- Spring Framwork를 학습하고 배운 내용을 활용하여 만든 개인 프로젝트입니다.
- 기본적인 기능과 디자인은 [velog.io](https://velog.io/)를 참고했습니다.

<br>

## 데모 - [Boolog](http://www.boolog.kro.kr)
- 테스트 계정
  - Email : test@test.com
  - Passwrod : test

<br>

## 프로젝트 진행 목적
- 프론트엔드와 백엔드를 모두 혼자서 설계하고 구현하면서 배우는 점이 있을 것이라 기대했습니다.
- 학습한 내용의 이해를 돕기 위해 프로젝트에 코드를 작성하면서 정리했습니다.

<br>

## 개발 기간
- 2022.06 ~ 2022.10

<br>

## 기획 및 설계
### API 명세
**게시글 (Post)**
|No|Method|URL|설명|
|--|------|---|--|
|1|`GET`|/post/{id}|게시글 단건 조회|
|2|`GET`|/post/popular|인기글 조회|
|3|`GET`|/post/recent|최신글 조회|
|4|`GET`|/tags/{tag}|태그별 조회|
|5|`GET`|/post/search?keyword=___|검색어 조회|
|6|`GET`|/post/liked|좋아요한 글 조회|
|7|`POST`|/post|게시글 등록|
|8|`PUT`|/post/{id}|게시글 수정|
|9|`DELETE`|/post/{id}|게시글 삭제|
|10|`POST`|/liked/{id}|게시글 좋아요|
|11|`DELETE`|/liked/{id}|게시글 좋아요 취소|

**회원 (User)**
|No|Method|URL|설명|
|------|---|---|---|
|1|`GET`|/@{name}|내 정보|
|2|`GET`|/setting|회원 정보 관리|
|3|`PATCH`|/setting|정보 수정|
|4|`DELETE`|/setting|회원 탈퇴|
|5|`GET`|/logout|로그아웃|
|6|`GET`|/signin|권한 없음(가입 안내 페이지)|

<br>

### ERD
![erd](https://user-images.githubusercontent.com/96276840/227703717-640eb16c-fc8f-4ce4-b956-8075391fbe89.jpg)
- 각각의 게시글이 가지고 있는 태그는 post 테이블에 담지 않고 별도의 테이블로 분리했습니다.
- post와 tag는 다대다 관계이므로, 이를 post_tag 테이블로 표현했습니다.

<br>

### 디자인
- https://ovenapp.io/view/ikRK1RPN6QbQdhMsl0va1UunRsdUZTj7/WqeHi

<br>

### 패키지 구조
```
📦boolog
 ┣ 📂config
 ┃ ┣ 📂security
 ┃ ┃ ┣ 📂auth
 ┃ ┃ ┃ ┣ 📜LoginSuccessHandler.java
 ┃ ┃ ┃ ┣ 📜PrincipalDetails.java
 ┃ ┃ ┃ ┗ 📜PrincipalDetailsService.java
 ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┣ 📜OAuthAttributes.java
 ┃ ┃ ┃ ┗ 📜SessionMember.java
 ┃ ┃ ┣ 📂oauth
 ┃ ┃ ┃ ┗ 📜CustomOAuth2UserService.java
 ┃ ┃ ┣ 📜CustomAccessDeniedHandler.java
 ┃ ┃ ┣ 📜CustomAuthenticationEntryPoint.java
 ┃ ┃ ┗ 📜SecurityConfig.java
 ┃ ┣ 📜AppConfig.java
 ┃ ┣ 📜LoginMember.java
 ┃ ┣ 📜LoginMemberArgumentResolver.java
 ┃ ┗ 📜WebConfig.java
 ┣ 📂domain
 ┃ ┣ 📂member
 ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┣ 📜MemberRepository.java
 ┃ ┃ ┃ ┣ 📜MemberRepositoryCustom.java
 ┃ ┃ ┃ ┗ 📜MemberRepositoryImpl.java
 ┃ ┃ ┣ 📜Member.java
 ┃ ┃ ┣ 📜MemberVO.java
 ┃ ┃ ┗ 📜Role.java
 ┃ ┗ 📂post
 ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┣ 📜PostRepository.java
 ┃ ┃ ┃ ┣ 📜PostRepositoryCustom.java
 ┃ ┃ ┃ ┗ 📜PostRepositoryImpl.java
 ┃ ┃ ┣ 📜LikedPost.java
 ┃ ┃ ┣ 📜Period.java
 ┃ ┃ ┣ 📜Post.java
 ┃ ┃ ┣ 📜PostTag.java
 ┃ ┃ ┗ 📜Tag.java
 ┣ 📂exception
 ┃ ┣ 📂response
 ┃ ┃ ┣ 📜ErrorResponse.java
 ┃ ┃ ┗ 📜ExceptionController.java
 ┃ ┣ 📜AuthenticationException.java
 ┃ ┣ 📜AuthorizationException.java
 ┃ ┣ 📜BoologException.java
 ┃ ┣ 📜ExistNameException.java
 ┃ ┣ 📜NoSuchMemberException.java
 ┃ ┗ 📜NoSuchPostException.java
 ┣ 📂service
 ┃ ┣ 📜MemberService.java
 ┃ ┗ 📜PostService.java
 ┣ 📂trigger
 ┃ ┣ 📜LikedMinus.java
 ┃ ┗ 📜LikedPlus.java
 ┣ 📂utils
 ┃ ┣ 📜AppUtils.java
 ┃ ┗ 📜WebUtils.java
 ┣ 📂web
 ┃ ┣ 📂dto
 ┃ ┃ ┣ 📜MemberResponseDto.java
 ┃ ┃ ┣ 📜MemberUpdateDto.java
 ┃ ┃ ┣ 📜PostRequestDto.java
 ┃ ┃ ┣ 📜PostResponseDto.java
 ┃ ┃ ┣ 📜PostUpdateDto.java
 ┃ ┃ ┗ 📜TagResponseDto.java
 ┃ ┣ 📜IndexController.java
 ┃ ┣ 📜MemberController.java
 ┃ ┗ 📜PostController.java
 ┣ 📜BoologApplication.java
 ┗ 📜TestDataInit.java
```

<br>

## 주요 기능
### 로그인 및 회원가입
![login-Signin](https://user-images.githubusercontent.com/96276840/225645443-3af992b2-655d-4600-b19b-c8c177b3163d.png)
- 이메일 인증 관련 기능 및 로직이 아직 미구현되어 있어 현재, 일반 회원가입은 불가능합니다.
- 구글, 네이버 OAuth 로그인을 지원합니다.

### 게시글 관련
#### 인기순 조회
![popular](https://user-images.githubusercontent.com/96276840/225658505-a980b42f-7225-43c3-804b-af3a3e5873d5.png)
- 좋아요 수가 많은 순서대로, 좋아요 수가 같다면 작성일을 기준으로 내림차순하여 정렬합니다.
- 일간, 주간, 월간, 연간으로 기간을 설정하여 조회할 수 있습니다.

#### 최신순 조회
![recent](https://user-images.githubusercontent.com/96276840/225659254-e758076e-bd68-4dc7-adcb-a7681683ebf6.png)
- 가장 최근에 작성된 순으로 내림차순 정렬합니다.

#### 키워드 조회
![keyword](https://user-images.githubusercontent.com/96276840/225661280-3aae99ff-0a55-48f2-8084-7564738e067b.png)
- 찾고자 하는 키워드가 포함된 게시글을 검색하고 작성일을 기준으로 내림차순하여 정렬합니다.
- 글제목, 글내용, 글소개, 유저이름에 키워드가 포함되어 있는지 확인합니다.

#### 좋아요한 글 조회
![liked](https://user-images.githubusercontent.com/96276840/225664062-1187d997-64fc-4b04-9fdb-5149288df986.png)
- 좋아요를 누른 게시글을 조회할 수 있습니다.
- 좋아요를 누른 날짜와 시간을 기준으로 내림차순하여 정렬합니다.

#### 작성 및 수정
![write](https://user-images.githubusercontent.com/96276840/225662377-f40a4535-ff20-4376-866e-a64e4a16c365.png)
![publish](https://user-images.githubusercontent.com/96276840/225662660-72a0d912-bad8-45fe-92b1-5a808efab469.png)
- 마크다운 또는 HTML 형식으로 작성 가능하도록 외부 라이브러리를 사용했습니다.
- 썸네일 관련 기능은 현재 미구현입니다.

### 회원 정보 관리
![user](https://user-images.githubusercontent.com/96276840/225666405-8f36f2f8-748b-453a-81ed-d77d2d39b16c.png)
- 프로필 이미지 관련 기능은 현재 미구현입니다.

<br>

## 사용한 기술
- Frontend : HTML/CSS, Javascript, Bootstrap
- Backend : Java, Spring, Thymeleaf, Spring Security, Spring Data JPA
- DevOps : MySQL, Google Compute Engine
- Tools : IntelliJ IDEA, VScode

<br>

## 사용한 외부 라이브러리
- 글 작성 에디터 - [toast ui editor](https://ui.toast.com/tui-editor)
- 태그 라이브러리 - [tagify](https://github.com/yairEO/tagify)

<br>

## 개발 기록
- 개발하면서 고민했던 점, 개선했던 점 등을 기록합니다.
  - https://stophase.notion.site/9d20799eb2274d7f8b49cab208010295

<br>

## 앞으로 추가할 기능
- **일반 로그인 및 회원가입**
  - 이메일 인증을 통해 유효하지 않은 이메일로 회원가입 방지
- **이미지 업로드**
  - 썸네일, 프로필, 게시글 내 이미지 삽입 관련 로직 구현
- **구독 기능**
  - 자주 방문하는 회원 구독하는 기능
  - 구독한 회원별로 게시글 모아볼 수 있는 조회 API 추가
