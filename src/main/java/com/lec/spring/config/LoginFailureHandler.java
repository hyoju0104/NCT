package com.lec.spring.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	@Override
	public void onAuthenticationFailure(
			HttpServletRequest request,
			HttpServletResponse response,
			AuthenticationException exception
	) throws IOException, ServletException {
		// 기본 에러 메시지
		String errorMsg = "아이디나 비밀번호가 틀렸습니다.";
		
		// 직접 DisabledException 이거나, 내부에 감싸인 경우 모두 잡아낸다
		Throwable cause = exception;
		if (exception instanceof DisabledException
				|| (exception instanceof InternalAuthenticationServiceException
				&& exception.getCause() instanceof DisabledException)) {
			// 메시지는 loadUserByUsername()에서 던진 메시지를 그대로 사용
			DisabledException de = exception instanceof DisabledException
					? (DisabledException) exception
					: (DisabledException) exception.getCause();
			errorMsg = de.getMessage();
		}
		
		// utf-8 로 인코딩하여 파라미터로 전달
		String encoded = URLEncoder.encode(errorMsg, "UTF-8");
		// 실패 리다이렉트 URL : /login?error=true&message=인코딩된메시지
		setDefaultFailureUrl("/login?error=true&message=" + encoded);
		
		super.onAuthenticationFailure(request, response, exception);
	}
}
