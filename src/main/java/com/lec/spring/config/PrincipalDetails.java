package com.lec.spring.config;

import com.lec.spring.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor //생성자로 User user를 자동으로 받게 해줌
public class PrincipalDetails implements UserDetails {

    private final User user;
    public User getUser() {
        return user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //collect 에는 ROLE_USER, ROLE_ADMIN 같은 권한을 담을 것
        return Collection<GrantedAuthority> collect = new ArrayList<>();
        //DB에서 실제로 로그인한 유저의 권한 목록 가져오는 구문
        // e.g. user.getId() → 3 >> selectAuthoritiesById(3) → [Authority(ROLE_USER), Authority(ROLE_ADMIN)]
        List<Authority> List = userService.selectAuthoritiesById(user.getId());
        for(Authority auth : list){
            collect.add(new GrantedAuthority(){
                @Override
                public String getAuthority() { // "GrantedAuthority 인터페이스를 구현한 객체"를 즉석에서 만듦
                    return auth.getName(); //e.g. "ROLE_USER", 다음엔 "ROLE_ADMIN",..
                    // ㄴ> 이렇게 하는 이유: Spring Security는 권한을 물을 때 그냥 문자열이 아닌 무조건 GrantedAuthority 타입으로 줘야 함.
                    // 즉, collect에 담는 것은 문자열이 아니고 GrantedAuthority객체
                }
            });
        }
        return collect;
    }
    /*
    e.g
    첫 번째 권한
    new GrantedAuthority() {
        public String getAuthority() {
            return "ROLE_USER";
        }
    }

    두 번째 권한
    new GrantedAuthority() {
        public String getAuthority() {
            return "ROLE_ADMIN";
        }
    }
    즉, 객체 그 자체가 들어감.
    "ROLE_ADMIN" 같은 문자열이 직접 들어가는 게 아니고,
    "ROLE_ADMIN이라는 문자열을 반환하는 getAuthority()라는 함수를 가진 객체"가 들어가요.
    */

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 활성화 되었는지
    @Override
    public boolean isEnabled() {
        return true;
    }

    // 계정 credential 이 만료된건 아닌지?
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 잠긴건 아닌지?
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정이 만료된건 아닌지?
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
}






















//시큐리티가 /user/login (POST) 주소요청이 오면 낚아채서 로그인을 진행시킨다.
//로그인(인증) 진행이 완료되면 '시큐리티 session' 에 넣어주게 된다.
//우리가 익히 알고 있는 같은 session 공간이긴 한데..
//시큐리티가 자신이 사용하기 위한 공간을 가집니다.
//=> Security ContextHolder 라는 키값에다가 session 정보를 저장합니다.
//여기에 들어갈수 있는 객체는 Authentication 객체이어야 한다.
//Authentication 안에 User 정보가 있어야 됨.
//User 정보 객체는 ==> UserDetails 타입 객체이어야 한다.

//따라서 로그인한 User 정보를 꺼내려면
//Security Session 에서
//   => Authentication 객체를 꺼내고, 그 안에서
//        => UserDetails 정보를 꺼내면 된다.
