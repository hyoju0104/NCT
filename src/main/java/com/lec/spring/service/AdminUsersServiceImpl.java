package com.lec.spring.service;

import com.lec.spring.repository.AdminUsersRepository;
import com.lec.spring.repository.UserRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AdminUsersServiceImpl implements AdminUsersService {
	
	private final AdminUsersRepository adminUsersRepository;
	private final UserRepository userRepository;
	
	public AdminUsersServiceImpl(SqlSession sqlSession, UserRepository userRepository) {
		this.adminUsersRepository = sqlSession.getMapper(AdminUsersRepository.class);
		this.userRepository = userRepository;
	}
	
	
	@Override
	public List<Map<String, Object>> getUsersByPlan() {
		return adminUsersRepository.countUsersByPlan();
	}
	
	@Override
	public List<Map<String, Object>> getUsersByStatus() {
		return adminUsersRepository.countUsersRentalStatus();
	}
	
	@Override
	public List<Map<String, Object>> getRentalsByBrand() {
		return adminUsersRepository.countRentalByBrand();
	}
	
	@Override
	public List<Map<String, Object>> getInactiveUsers() {
		return adminUsersRepository.findInactiveUsers();
	}
	
	
	@Override
	@Transactional
	public void releaseSuspension(Long userId) {
		// status_account 를 ACTIVE 로 복구
		userRepository.updateStatusAccount(userId, "ACTIVE");
	}
	
}
