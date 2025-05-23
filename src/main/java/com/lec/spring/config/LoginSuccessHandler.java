package com.lec.spring.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import java.io.IOException;

/*
SimpleUrlAuthenticationSuccessHandler: 스프링 시큐리티에서 제공하는 "로그인 성공 후 처리기"
이걸 상속받으면, 로그인 성공 후 어떤 페이지로 보낼지 직접 결정 가능
*/
// 로그인 성공 이후에 원하는 동작을 정의하는 클래스
// SimpleUrlAuthenticationSuccessHandler 를 상속하여 커스터마이징 가능
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        /*
        request	: 어떤 주소를 눌렀는지, 로그인 폼에서 어떤 값을 입력했는지 같은 정보
        response : 서버의 답변. 어디로 이동시킬 지 결정
        authentication : 로그인한 사람이 누구인지, 어떤 권한(ADMIN, USER 등)이 있는지 정보
        */

        // 사용자가 로그인 전에 요청했던 URL 정보를 세션에서 꺼냄
        // 이 정보는 Spring Security 가 자동으로 저장해둠
        //HttpSessionRequestCache : "사용자가 로그인하기 전에 요청한 주소"를 세션(HttpSession) 안에 저장해 두는 저장소 역할
        SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
        //request, response는 지금 현재 로그인 요청에 대한 정보->그걸 기준으로 세션에서 해당 사용자의 이전 요청(SavedRequest)을 꺼냄

        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
            // Spring Security 가 미리 만들어 둔 '리다이렉트 방식' 꺼내서, request/response를 이용해 targetUrl로 이동시키기
        } else {
            // 로그인 전 요청한 주소가 없으면, 부모가 정의한 기본 이동 방식 사용 (예: /post/list)
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}

/*
로그인 안 한 사용자가 /user/withdraw를 입력함

Spring Security가 이 URL 접근을 막고, /login으로 보냄

이때 “아 얘가 원래 가려던 주소가 /user/withdraw였구나” 라고 기록을 남김

그 기록을 HttpSessionRequestCache라는 곳에 세션 기준으로 저장
*/

/*
SavedRequest 안에는 이런 정보가 들어있어요
사용자가 원래 요청한 URL (예: /user/withdraw)

요청 방식 (GET, POST 등)

파라미터 등 기타 정보

우리는 여기서 가장 중요한 "원래 URL" 을 꺼내서 getRedirectUrl()로 사용합니다.

*/