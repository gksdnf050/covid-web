package com.covid.web.service.user.impl;

import java.util.ArrayList;
import java.util.List;

import com.covid.web.dto.user.User;
import com.covid.web.service.user.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.covid.web.dto.user.UserRole;
import com.covid.web.mapper.user.UserMapper;
import com.covid.web.mapper.user.UserRoleMapper;
import com.covid.web.dto.user.UserEntity;
import com.covid.web.dto.user.UserRoleEntity;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
	private final UserMapper userMapper;
	private final UserRoleMapper userRoleMapper;

	public UserServiceImpl(UserMapper userMapper, UserRoleMapper userRoleMapper) {
		this.userMapper = userMapper;
		this.userRoleMapper = userRoleMapper;
	}

	@Transactional(readOnly = false)
	public void signUp(User user) {
		// 비밀번호 암호화
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		userMapper.insertUser(user);
		int userId = user.getId();

		UserRole userRole = new UserRole(userId, "ROLE_USER");

		userRoleMapper.insertUserRole(userRole);
	}

	@Override
	public UserEntity getUser(String loginUserId) {
		User user = userMapper.getUserByEmail(loginUserId);

		if (user == null)
			return null;

		return new UserEntity(user.getEmail(), user.getPassword());
	}

	@Override
	public List<UserRoleEntity> getUserRoles(String loginUserId) {
		List<UserRole> userRoles = userRoleMapper.getRolesByEmail(loginUserId);
		List<UserRoleEntity> list = new ArrayList<>();

		for (UserRole userRole : userRoles) {
			list.add(new UserRoleEntity(loginUserId, userRole.getRoleName()));
		}
		return list;
	}
}
