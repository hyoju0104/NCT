package com.lec.spring.config;

import com.lec.spring.domain.Brand;
import com.lec.spring.domain.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    
    // HttpSessionRequestCache는 new로 써도 되고, 빈으로 등록해도 됩니다.
    private final HttpSessionRequestCache requestCache;
    
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        // 1) 이전 요청 URL이 있는지 확인
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            response.sendRedirect(savedRequest.getRedirectUrl());
            return;
        }
        
        Object principal = authentication.getPrincipal();
        HttpSession session = request.getSession();
        
        // 2) BRAND 계정 처리
        if (principal instanceof com.lec.spring.config.PrincipalBrandDetails brandDetails) {
            Brand brand = brandDetails.getBrand();
            session.setAttribute("brandId", brand.getId());
            session.setAttribute("brandUsername", brand.getUsername());
        }
        
        // 3) USER 계정 처리
        if (principal instanceof com.lec.spring.config.PrincipalUserDetails userDetails) {
            User user = userDetails.getUser();
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
        }
        
        // 4) 권한별 기본 리다이렉트
        boolean isBrand = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("BRAND"));
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));
        
        if (isBrand) {
            response.sendRedirect("/brand/list");
        } else if (isAdmin) {
            response.sendRedirect("/admin/sales");
        } else {
            response.sendRedirect("/post/list");
        }
    }
}
