package com.covid.web.service;

import java.util.ArrayList;
import java.util.List;

import com.covid.web.dto.UserDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.covid.web.dto.UserRoleDto;
import com.covid.web.mapper.UserMapper;
import com.covid.web.mapper.UserRoleMapper;
import com.covid.web.service.security.UserEntity;
import com.covid.web.service.security.UserRoleEntity;
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
	public void signUp(UserDto userDto) {
		// 비밀번호 암호화
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

		userMapper.insertUser(userDto);
		int userId = userDto.getId();

		UserRoleDto userRoleDto = new UserRoleDto(userId, "ROLE_USER");

		userRoleMapper.insertUserRole(userRoleDto);
	}

	@Override
	public UserEntity getUser(String loginUserId) {
		UserDto userDto = userMapper.getUserByEmail(loginUserId);

		if (userDto == null)
			return null;

		return new UserEntity(userDto.getEmail(), userDto.getPassword());
	}

	@Override
	public List<UserRoleEntity> getUserRoles(String loginUserId) {
		List<UserRoleDto> userRoleDtos = userRoleMapper.getRolesByEmail(loginUserId);
		List<UserRoleEntity> list = new ArrayList<>();

		for (UserRoleDto userRoleDto : userRoleDtos) {
			list.add(new UserRoleEntity(loginUserId, userRoleDto.getRoleName()));
		}
		return list;
	}
}
