# Boolog
>기존에 진행하고 있던 프로젝트([Boolog-MyBatis](https://github.com/Ji-Hwan-Jung/boolog-mybatis))를 JPA로 전환한 프로젝트입니다.<br>
기존 프로젝트 개발은 무기한 중단하며, 현재 프로젝트를 기본으로 개발할 예정입니다.

- Spring Framwork를 학습하고 배운 내용을 활용하여 만든 토이 프로젝트입니다.
- 기본적인 기능과 디자인은 [velog.io](https://velog.io/)를 참고했습니다.
- 학습한 것들을 실제 프로젝트에 녹이는 것을 목표로 하는 프로젝트이므로 수시로 기능을 추가하거나 리팩토링 할 예정입니다.
<br>

## 데모 - [Boolog](http://www.boolog.kro.kr)
- 무료 서비스를 주로 사용하고 있어서 성능이 좋지 않아 전체적으로 로드 속도가 다소 느립니다.


- 테스트 계정
  - Email : test@test.com
  - Passwrod : test

<br>

## 기존 프로젝트에서의 변동 사항
- Entity를 중심으로 DB 테이블 구조 재설계
- MyBatis 기반에서 JPA 기반으로 전환

<br>

## 주요 기능
- 로그인/회원가입 : 구글, 네이버 OAuth API 사용
- 게시글 기능
  - 게시글 검색
    - 인기순 : 일간, 주간, 월간, 연간별로 검색 가능
    - 최신순 : 가장 최신에 작성된 게시글 순서대로 검색
    - 키워드 검색 : 찾고자 하는 키워드가 포함된 게시글 검색
  - 게시글 작성/수정 : 마크다운 또는 HTML 형식으로 작성 가능한 에디터 제공
  - 게시글 삭제
- 댓글 기능 : [라이브리](https://www.livere.com/) 서비스 사용
- 좋아요 기능 : 좋아요한 게시글을 따로 모아서 검색 가능
- 태그 기능 : 태그별로 게시글을 따로 모아서 검색 가능
  
<br>

## 기술 스택
- Frontend : HTML/CSS, Javascript, Bootstrap, Thymeleaf
- Backend : Java11, Spring Boot, Spring MVC, Spring Security, Spring Session, Spring Data JPA
- DevOps : Gradle, MySQL, Redis, Google Compute Engine
- Tools : IntelliJ IDEA, VScode

<br>

## 기획 및 설계
- [기능 명세서](https://stophase.notion.site/641b6f9b214f4c89ad7f0d53ff5470a4)
- [API 설계](https://stophase.notion.site/API-4f26a39fce3349d7919e84e773bef83a)
- [DB 스키마](https://www.erdcloud.com/d/g7m9tyvTkhDAhJcwt)

<br>

## 사용한 외부 라이브러리
- 글 작성 에디터 - [toast ui editor](https://ui.toast.com/tui-editor)

<br>

## 앞으로 추가 및 수정할 기능
- **일반 로그인 및 회원가입**
  - 이메일 인증을 통해 유효하지 않은 이메일로 회원가입 방지
- **댓글 기능**
  - 라이브리 서비스에 의존하지 않고 자체적으로 댓글 기능 구현
- **이미지 업로드**
  - 썸네일 및 게시글 내 이미지 삽입 관련 로직 구현
- **구독 기능**
  - 자주 방문하는 회원 구독하는 기능
  - 구독한 회원별로 게시글 모아볼 수 있는 조회 API 추가
- **알림 기능**
  - 구독한 회원의 새로운 글 등록, 나의 게시글에 달린 댓글 등등의 소식을 알려주는 기능

<br>

## 학습한 강의 및 도서
- [우아한형제들 최연소 기술이사 김영한의 스프링 완전 정복](https://www.inflearn.com/roadmaps/373)
- [스프링부트 시큐리티 & JWT 강의](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0)
- [스프링 부트와 AWS로 혼자 구현하는 웹 서비스](http://www.yes24.com/Product/Goods/83849117)
