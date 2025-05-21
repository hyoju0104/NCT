package com.lec.spring.service;

import com.lec.spring.domain.Authority;
import com.lec.spring.domain.Payment;
import com.lec.spring.domain.Plan;
import com.lec.spring.domain.User;
import com.lec.spring.repository.AuthorityRepository;
import com.lec.spring.repository.PaymentRepository;
import com.lec.spring.repository.PlanRepository;
import com.lec.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service 구현체 클래스
 * - 실제 서비스 로직을 수행하는 핵심 클래스
 * - SqlSession을 이용해 Repository(MyBatis Mapper) 주입
 * - 회원 정보 처리, 권한 연결, 비밀번호 암호화 등 업무 처리 담당
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    //비밀번호 암호화
    private final PasswordEncoder passwordEncoder;
    //DB에서 사용자, 권한 정보 조회
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PlanRepository planRepository;
    private final PaymentRepository paymentRepository;
    
    @Override
    public User findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * 사용자 아이디로 User 정보 조회 (로그인 시 사용됨)
     * → DB에서 사용자 검색 (대소문자 구분 막기 위해 대문자로 통일)
     */
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    /**
     * 회원 가입 전 아이디 중복 확인
     * → 아이디가 존재하면 true, 없으면 false
     */
    @Override
    public boolean isExist(String username) {
        return findByUsername(username) != null;
    }

    /**
     * 회원가입 처리
     * 1) 아이디 대문자로 저장
     * 2) 비밀번호 암호화해서 저장
     * 3) DB에 저장 후
     * 4) 기본 권한(ROLE_USER) 부여
     */
    @Override
    public int register(User user){
        //비밀번호 암호화
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 기본 권한은 ROLE_USER
        Authority auth = authorityRepository.findByGrade("USER");
        if (auth != null) {
            user.setAuthId(auth.getId());  // 권한 ID 설정
        }
        user.setStatusPlan("ACTIVE");
        user.setStatusAccount("ACTIVE");
        user.setPoint(0);
        user.setRentalCnt(0);
        //DB에 사용자 저장
        int result = userRepository.save(user);
        System.out.println("INSERT result = " + result);  // 추가: insert가 되었는지 확인
        System.out.println("user.getId() = " + user.getId()); // 추가: 키가 들어왔는지 확인

        if(user.getId() == null) {
            throw new RuntimeException("회원가입 실패: user.getId()가 null입니다.");
        }
        authorityRepository.addAuthority(user.getId(), auth.getId());
        return 1;
    }
    /**
     * 로그인한 사용자 ID로 권한 목록 조회
     * → Spring Security에서 로그인 시 getAuthorities() 호출용
     */
    @Override
    public List<Authority> selectAuthoritiesById(Long id) {
        User user = userRepository.findById(id); // 사용자 조회
        // 해당 사용자에 연결된 권한 목록 조회
        return authorityRepository.findByUser(user);
    }

    @Override
    public void updateUserInfo(User user) {
        String encPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encPassword);
        userRepository.updateUserInfo(user);
    }

    @Override
    @Transactional
    public void refundPoint(Long id, Integer amount) {
        User user = userRepository.findById(id);
        if (user.getPoint() < amount) throw new IllegalArgumentException("잔여 포인트 부족");
//        userRepository.refundPoint(id, amount);
        System.out.println("💡 REFUND: id=" + id + ", amount=" + amount);
        int result = userRepository.refundPoint(id, amount);
        System.out.println("💡 update result = " + result);

    }

    @Override
    @Transactional
    public void createPayment(Long userId) {
        User user = userRepository.findById(userId);
        Plan plan = planRepository.findByPlanId(user.getPlanId());
        if (plan == null) throw new IllegalStateException("유효한 플랜이 없습니다.");

        // Payment 테이블에 기록
        Payment payment = Payment.builder()
                .userId(userId)
                .planId(plan.getId())
                .price(plan.getPrice())
                .build();
        paymentRepository.save(payment);

        userRepository.updatePaidAt(userId);
    }

    @Override
    public void updateUserPlanId(Long id, Long planId) {
        userRepository.updateUserPlanId(id, planId);
    }

    @Override
    public void markAsDeleted(Long userId) {
        userRepository.updateStatusToDeleted(userId);
    }

    @Override
    public void inactivateUser(Long userId) {
        userRepository.updateStatusAccount(userId, "INACTIVE");
    }

    @Override
    public String findUserStatus(Long userId) {
        return userRepository.findStatusAccountById(userId);
    }

    @Override
    public void updateUserAddress(User user) {
        userRepository.updateUserAddress(user);
    }

    @Override
    public void updateUserPhoneNum(User user) {
        userRepository.updateUserPhoneNum(user);
    }

}
