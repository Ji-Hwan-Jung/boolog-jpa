package com.stoph.boolog.config.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@EqualsAndHashCode
@ToString
@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String email;
    private String name;
    private String picture;
    private String provider;
    private String providerId;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String email, String name, String picture, String provider, String providerId) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.email = email;
        this.name = name;
        this.picture = picture;
        this.provider = provider;
        this.providerId = providerId;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {

        switch (registrationId.toLowerCase()) {
            case "google": return ofGoogle(userNameAttributeName, attributes);
            case "naver" : return ofNaver("id", attributes);
            default: throw new IllegalArgumentException("현재 지원하지 않는 소셜 로그인 입니다.");
        }
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {

        return OAuthAttributes.builder()
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .name("google_"+(String) attributes.get("sub"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .provider("google")
                .providerId((String) attributes.get("sub"))
                .build();
    }

    public static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .name("naver_"+(String) response.get("id"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .provider("naver")
                .providerId((String) response.get("id"))
                .build();
    }
}
