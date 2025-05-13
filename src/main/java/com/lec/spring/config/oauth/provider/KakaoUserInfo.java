package com.lec.spring.config.oauth.provider;

import java.util.Map;

/**
 * Kakao OAuth2 로그인 시 받아온 사용자 정보를 처리하는 클래스입니다.
 */
public class KakaoUserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;
    /*
    {
      "id": 12345678,
      "properties": {
        "nickname": "홍길동"
      },
      "kakao_account": {
        "email": "gildong@kakao.com"
      }
    }
 */
    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }


    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("id")); // Long → String 변환
    }

    @Override
    public String getName() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        return (String) properties.get("nickname");
    }

    @Override
    public String getEmail() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        return (String) kakaoAccount.get("email");
    }

    @Override
    public String getProvider() {
        return "kakao";
    }
}
