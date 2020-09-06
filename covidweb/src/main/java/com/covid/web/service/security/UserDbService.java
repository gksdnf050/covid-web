package com.covid.web.service.security;

import java.util.List;

import com.covid.web.dto.Member;

public interface UserDbService {
	public void joinUser(Member member);
	public UserEntity getUser(String loginUserId);
	public List<UserRoleEntity> getUserRoles(String loginUserId);
}
