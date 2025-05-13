package com.lec.spring.config.oauth.provider;

public interface OAuth2UserInfo {
    String getProvider();  // "google", "facebook" ...
    String getProviderId(); // PK값
    String getEmail();
    String getName();
}
