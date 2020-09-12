package com.covid.web.mapper;

import java.util.List;

import com.covid.web.dto.UserRoleDto;

public interface UserRoleMapper {
	public List<UserRoleDto> getRolesByEmail(String email);
	public int insertUserRole(UserRoleDto role);
}
