package com.lec.spring.config;

import com.lec.spring.domain.User;
import com.lec.spring.repository.UserRepository;
import com.lec.spring.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        // 이미 응답이 완료되었다면 리다이렉트하지 않음
        if (response.isCommitted()) {
            log.warn("응답이 이미 커밋되어 리다이렉트할 수 없습니다.");
            return;
        }
        
        response.sendRedirect("/post/list");
    }

}
