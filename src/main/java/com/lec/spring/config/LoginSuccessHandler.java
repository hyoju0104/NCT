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
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    
    private final HttpSessionRequestCache requestCache;
    
    /** 실제 우리 서비스 내부에 만든(허용할) URL 패턴들 */
    private static final List<String> ALLOWED_URLS = List.of(
            "/.well-known", "/appspecific",
            "/login", "/register",
            "/css/", "/js/", "/images/", "/upload/", "/common/",
            "/post/list", "/post/detail", "/comment/",
            "/item/list", "/item/detail", "/item/list/category",
            "/brand/", "/admin/", "/user/withdraw"
    );
    
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        // 1) 저장된 요청이 있으면 꺼내서 Path만 추출
        SavedRequest saved = requestCache.getRequest(request, response);
        String savedRedirect = null;
        boolean invalidSavedRequest = false;
        if (saved != null) {
            String fullUrl = saved.getRedirectUrl();
            String path;
            try {
                path = new URL(fullUrl).getPath();
            } catch (MalformedURLException e) {
                path = "";
            }
            // 2) Path가 허용 목록에 없으면 잘못된 접근 플래그
            boolean allowed = ALLOWED_URLS.stream().anyMatch(path::startsWith);
            if (allowed) {
                savedRedirect = fullUrl;
            } else {
                invalidSavedRequest = true;
            }
        }
        
        // 3) 세션에 브랜드/유저 정보 저장
        HttpSession session = request.getSession();
        Object principal = authentication.getPrincipal();
        if (principal instanceof PrincipalBrandDetails bd) {
            Brand brand = bd.getBrand();
            session.setAttribute("brandId", brand.getId());
            session.setAttribute("brandUsername", brand.getUsername());
        }
        if (principal instanceof PrincipalUserDetails ud) {
            User user = ud.getUser();
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
        }
        
        // 4) 권한별 기본 URL 정하기
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));
        boolean isBrand = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("BRAND"));
        String defaultUrl;
        if (isAdmin) {
            defaultUrl = request.getContextPath() + "/admin/sales";
        } else if (isBrand) {
            defaultUrl = request.getContextPath() + "/brand/list";
        } else {
            defaultUrl = request.getContextPath() + "/post/list";
        }
        
        // 5) 잘못된 저장된 요청이면 alert + 기본 URL로 이동
        if (invalidSavedRequest) {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html><html><head><meta charset='UTF-8'></head><body>");
                out.println("<script>");
                out.println("  alert('잘못된 접근 경로입니다. 메인 페이지로 이동합니다.');");
                out.println("  location.replace('" + defaultUrl + "');");
                out.println("</script>");
                out.println("</body></html>");
                out.flush();
            }
            return;
        }
        
        // 6) 저장된 요청이 유효하면 그 URL로, 아니면 기본 URL로
        if (savedRedirect != null) {
            response.sendRedirect(savedRedirect);
        } else {
            response.sendRedirect(defaultUrl);
        }
    }
}
