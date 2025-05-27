package com.lec.spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@SpringBootTest
class WearupApplicationTests {
    
    static class OAuth2TestConfig {
        // OAuth2 client repository dummy bean
        @Bean
        public ClientRegistrationRepository clientRegistrationRepository() {
            return new InMemoryClientRegistrationRepository();
        }
        
        // OAuth2 authentication client storage dummy bean
        @Bean
        public OAuth2AuthorizedClientService authorizedClientService(ClientRegistrationRepository registrations) {
            return new InMemoryOAuth2AuthorizedClientService(registrations);
        }
    }

    @Test
    void contextLoads() {
        // 컨텍스트 로드만 검증
    }

}
