package com.lec.spring.config.oauth;


import com.lec.spring.config.PrincipalDetails;
import com.lec.spring.config.oauth.provider.GoogleUserInfo;
import com.lec.spring.config.oauth.provider.KakaoUserInfo;
import com.lec.spring.config.oauth.provider.NaverUserInfo;
import com.lec.spring.config.oauth.provider.OAuth2UserInfo;
import com.lec.spring.domain.User;
import com.lec.spring.repository.AuthorityRepository;
import com.lec.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * OAuth2UserService<OAuth2UserRequest, OAuth2User>(I)
 * └─ DefaultOAuth2UserService
 */

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Value("${spring.security.user.password}")
    private String oauth2Password; // OAuth 로그인 사용자의 비밀번호 대체용 임시 비밀번호

    @Autowired
    private UserService userService;

    private final PasswordEncoder passwordEncoder;
    @Autowired
    private AuthorityRepository authorityRepository;

    public PrincipalOauth2UserService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * OAuth2 로그인이 완료되면 자동으로 호출되는 메서드
     * 외부 로그인 공급자(구글, 네이버, 카카오 등)로부터 받은 사용자 정보를 처리함
     *
     * @param userRequest OAuth2UserRequest 객체
     *                    - 외부 로그인 제공자의 정보(예: 구글인지 네이버인지)
     *                    - 클라이언트 ID, Secret 등의 정보를 포함함
     *                    - 토큰(access token) 정보 포함
     * @return OAuth2User 인증된 사용자 정보 객체
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // DefaultOAuth2UserService 가 제공하는 메서드 사용하여
        // 실제 외부 제공자(Google, Naver, Kakao)로부터 사용자 정보 받아오기
        OAuth2User oAuth2User = super.loadUser(userRequest);
        // 어떤 플랫폼(Google, Naver, Kakao)으로 로그인했는지 확인하기 위해 provider명 추출
        String provider = userRequest.getClientRegistration().getRegistrationId();   // "google"
        System.out.println("🟨 provider = " + provider);  // 기대값: kakao

        // 외부 제공자로부터 받은 사용자 정보를 공통 인터페이스인 OAuth2UserInfo 로 변환
        // 각 플랫폼마다 제공하는 데이터 형식이 다르기 때문에 분기 처리 필요
        OAuth2UserInfo oAuth2UserInfo = switch (provider) {
            //아까 loadUser를 통해 Map<> attributes 받아왔지? 그걸 생성자에 넣어줘서 이 Map 전달해주는거야
            case "google" -> new GoogleUserInfo(oAuth2User.getAttributes());
            case "naver" -> new NaverUserInfo(oAuth2User.getAttributes());
            case "kakao" -> new KakaoUserInfo(oAuth2User.getAttributes());
            default -> null;
        };
        // 외부 제공자가 부여한 고유 ID
        String providerId = oAuth2UserInfo.getProviderId();
        // username은 중복되지 않도록 만들어야 한다
        String username = provider + "_" + providerId; // "ex) google_xxxxxxxx"
        // 외부 로그인 사용자는 직접 비밀번호를 입력하지 않으므로 임의의 비밀번호를 저장 (보안을 위해 설정 필요)
        String password = oauth2Password;
        // 외부 제공자로부터 받은 이메일과 이름 정보 추출
        String email = oAuth2UserInfo.getEmail();
        String name = oAuth2UserInfo.getName();
        Long authId = authorityRepository.findByGrade("USER").getId();
        // 위에서 수집한 정보를 바탕으로 우리 시스템의 User 객체 생성
        User newUser = User.builder()
                .username(username)
                .name(name)
                .email(email)
                .password(password)
                .authId(authId)
                .statusPlan("INACTIVE")
                .provider(provider)
                .providerId(providerId)
                .build();
        // DB에 동일한 username을 가진 사용자가 이미 존재하는지 확인
        User user = userService.findByUsername(username);
        if (user == null) {  // 비가입자인 경우에만 회원 가입 진행
            user = newUser;
            int cnt = userService.register(user);  // 회원 가입처리(DB저장)
            if (cnt > 0) {
                System.out.println("🎄[회원 가입 성공]");
                user = userService.findByUsername(username);  // 다시 DB에서 읽어와야 한다. regDate 등의 정보
            } else {
                System.out.println("🎄[회원 가입 실패]");
            }
        } else {
            System.out.println("🎄이미 가입된 회원입니다");
        }

        // Spring Security가 인증된 사용자 정보를 담을 수 있도록 PrincipalDetails 객체에 사용자 정보와 attributes(제공자 정보) 전달
        PrincipalDetails principalDetails = new PrincipalDetails(user, oAuth2User.getAttributes());
        // 내부적으로 필요한 userService 를 PrincipalDetails 에도 주입
        principalDetails.setUserService(userService);

        // 최종적으로 인증된 사용자 객체를 반환
        return principalDetails;
    }
}

















