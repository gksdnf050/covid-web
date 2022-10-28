package com.covid.web.service.user;

import java.util.List;

import com.covid.web.dto.user.User;
import com.covid.web.dto.user.UserEntity;
import com.covid.web.dto.user.UserRoleEntity;

public interface UserDbService {
	void signUp(User user);
	UserEntity getUser(String loginUserId);
	List<UserRoleEntity> getUserRoles(String loginUserId);
}
