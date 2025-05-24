package com.lec.spring.config;

import com.lec.spring.config.oauth.PrincipalOauth2UserService;
import com.lec.spring.domain.Brand;
import com.lec.spring.domain.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

@Configuration // 이 클래스는 설정용
@EnableWebSecurity // Spring Securirty 보안 기능 켬
@RequiredArgsConstructor //final 로 선언된 변수를 자동으로 생성자에서 넣어줌
public class SecurityConfig {

    //--------------------------------------------------------
    // OAuth 로그인

    @Lazy
    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Lazy
    @Autowired
    private CustomOAuth2SuccessHandler customOAuth2SuccessHandler;
    
    private final LoginFailureHandler loginFailureHandler;
    private final LoginSuccessHandler loginSuccessHandler;


    //비밀번호 암호화
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
    // AuthenticationManager : 인증 처리 관리자. 로그인 시도 시 유효한 사용자인지, ID&PW 일치하는지 확인
    // AuthenticationConfiguration config : Spring Security가 내부적으로 가지고 있는 로그인 설정 정보들이 모여있음
    // 그 안에 이미 만들어진 AuthenticationManager가 들어있음 > 그걸 꺼내서 @Bean 으로 등록
    
    // 순환참조를 막기 위해 static(정적)으로 선언
    @Bean
    public static HttpSessionRequestCache httpSessionRequestCache() {
        return new HttpSessionRequestCache();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // SecurityFilterChanin을 반환: 보안 규칙이 담긴 상자를 만들어서 반환
        // HttpSecurity http: 보안 설정 도구. 어떤 페이지에 누가 들어올 수 있는지 설정
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .authorizeHttpRequests(auth->auth
                        // 누구나 접근 가능
                        .requestMatchers(
                                "/", "/error",
                                "/login", "/register/**",
                                "/css/**", "/js/**", "/images/**", "/upload/**", "/common/**",
                                "/post/list", "/post/detail/**",
                                "/comment/**",
                                "/item/**", "/item/detail/**", "/item/list/category/**",
                                
                                // DevTools PWA 메타파일 경로
                                "/.well-known/**", "/appspecific/**"
                        ).permitAll()
                        
                        // 특정 권한을 가진 계정만 접근 가능
                        .requestMatchers("/brand/**").hasAuthority("BRAND")
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/user/withdraw").authenticated()
                        
                        // 그 외 모든 주소는 로그인한 사람만 접근 가능
                        .anyRequest().authenticated()
                )   // authorizeHttpRequests

                
                .formLogin(form->form // 로그인화면을 어떻게 보여줄지
                        .loginPage("/login") // 로그인 페이지 설정
                        .loginProcessingUrl("/login") // 아이디 비번 입력하고 로그인 버튼 누르면 /login 으로 전송
                                                      // 근데 이때 Spring Security가 이 요청을 가로채서 로그인 처리 자동으로 해줌
                                                      // 권한별 리다이렉트 핸들러
                        // 로그인 실패 시 처리할 핸들러
                        .failureHandler(loginFailureHandler)
                        // 로그인 성공 시 처리할 핸들러
                        .successHandler(loginSuccessHandler)
                        // 위에서 설정한 로그인 관련 URL 들은 로그인 안해도 누구나 접근 가능
                        .permitAll()
                        
                ) // formLogin


                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll() // 로그아웃 페이지도 아무나 다 볼수있게~
                )//logout


                .oauth2Login(httpSecurity->httpSecurity
                                .loginPage("/login") // 로그인 페이지를 동일한 url로 지정

//                        .defaultSuccessUrl("/post/list",true) // 로그인 성공 후 이동할 기본 URL
                                /*
                                 oauth2 로그인 시
                                  → Authorization code 발급 → access token 발급 → 사용자 정보(profiles 등) 요청
                                     ↓
                                [userInfoEndpoint().userService(...)]
                                  → PrincipalOauth2UserService.loadUser() 실행
                                 */

//                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                // 인증서버의 userInfo Endpoint(후처리) 설정
                                // 후처리: 회원가입 + 로그인 진행
//                                .userService(principalOauth2UserService)
//                        )

                                .userInfoEndpoint(userInfo -> userInfo
                                        .userService(principalOauth2UserService)
                                )
                                .successHandler(customOAuth2SuccessHandler)
                ); // oauth2Login, 메소드 체이닝 끝
        
        return http.build(); // 지금까지 설정한 모든 보안 규칙을 하나로 묶어 스프링에 넘겨줌

    }//SecurityFilterChain

} // SecurityConfig
