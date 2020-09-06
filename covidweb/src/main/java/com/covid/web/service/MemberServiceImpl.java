package com.covid.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.covid.web.dto.Member;
import com.covid.web.dto.MemberRole;
import com.covid.web.mapper.MemberDao;
import com.covid.web.mapper.MemberRoleDao;
import com.covid.web.service.security.UserEntity;
import com.covid.web.service.security.UserRoleEntity;

@Service
public class MemberServiceImpl implements MemberService {
	private final MemberDao memberDao;
	private final MemberRoleDao memberRoleDao;

	public MemberServiceImpl(MemberDao memberDao, MemberRoleDao memberRoleDao) {
		this.memberDao = memberDao;
		this.memberRoleDao = memberRoleDao;
	}

	@Override
	public UserEntity getUser(String loginUserId) {
		Member member = memberDao.getMemberByEmail(loginUserId);
		return new UserEntity(member.getEmail(), member.getPassword());
	}

	@Override
	public List<UserRoleEntity> getUserRoles(String loginUserId) {
		List<MemberRole> memberRoles = memberRoleDao.getRolesByEmail(loginUserId);
		List<UserRoleEntity> list = new ArrayList<>();

		for (MemberRole memberRole : memberRoles) {
			list.add(new UserRoleEntity(loginUserId, memberRole.getRoleName()));
		}
		return list;
	}
}
