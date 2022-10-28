package com.covid.web.repository;

import com.covid.web.model.entity.UserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository {
	List<UserRole> getRolesByEmail(String email);
	int insertUserRole(UserRole role);
}
