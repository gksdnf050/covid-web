package com.covid.web.mapper;

import java.util.List;

import com.covid.web.dto.MemberRole;

public interface MemberRoleDao {

	public List<MemberRole> getRolesByEmail(String email);
		
	
}
