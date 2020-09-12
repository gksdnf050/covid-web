package com.covid.web.service.security;

import java.util.List;

import com.covid.web.dto.UserDto;

public interface UserDbService {
	public void signUp(UserDto userDto);
	public UserEntity getUser(String loginUserId);
	public List<UserRoleEntity> getUserRoles(String loginUserId);
}
