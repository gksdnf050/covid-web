package com.covid.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.covid.web.model.entity.User;
import com.covid.web.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.covid.web.model.entity.UserRole;
import com.covid.web.repository.UserRepository;
import com.covid.web.repository.UserRoleRepository;
import com.covid.web.model.entity.UserEntity;
import com.covid.web.model.entity.UserRoleEntity;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;

	public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository) {
		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
	}

	@Transactional(readOnly = false)
	public void signUp(User user) {
		// 비밀번호 암호화
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		userRepository.insertUser(user);
		int userId = user.getId();

		UserRole userRole = new UserRole(userId, "ROLE_USER");

		userRoleRepository.insertUserRole(userRole);
	}

	@Override
	public UserEntity getUser(String loginUserId) {
		User user = userRepository.getUserByEmail(loginUserId);

		if (user == null)
			return null;

		return new UserEntity(user.getEmail(), user.getPassword());
	}

	@Override
	public List<UserRoleEntity> getUserRoles(String loginUserId) {
		List<UserRole> userRoles = userRoleRepository.getRolesByEmail(loginUserId);
		List<UserRoleEntity> list = new ArrayList<>();

		for (UserRole userRole : userRoles) {
			list.add(new UserRoleEntity(loginUserId, userRole.getRoleName()));
		}
		return list;
	}
}
