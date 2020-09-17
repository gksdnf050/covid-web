package com.covid.web.mapper.user;

import com.covid.web.dto.user.UserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleMapper {
	List<UserRole> getRolesByEmail(String email);
	int insertUserRole(UserRole role);
}
