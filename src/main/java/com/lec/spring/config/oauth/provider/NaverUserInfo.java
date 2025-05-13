package com.lec.spring.config.oauth.provider;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;

    public NaverUserInfo(Map<String, Object> attributes) {
        this.attributes = (Map)attributes.get("response");
    }

    /*
    네이버 attributes 정보
    {
      "resultcode": "00",
      "message": "success",
      "response": {
        "id": "1234567890",
        "email": "user@naver.com",
        "name": "홍길동",
        "nickname": "길동이" /// 이 response를 꺼내와
      }
    }

    * */

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return (String)attributes.get("id");
    }

    @Override
    public String getEmail() {
        return (String)attributes.get("email");
    }

    @Override
    public String getName() {
        return (String)attributes.get("name");
    }
}
