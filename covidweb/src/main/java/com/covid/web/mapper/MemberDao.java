package com.covid.web.mapper;

import com.covid.web.dto.Member;

public interface MemberDao {
	public Member getMemberByEmail(String email);
	public void joinUser(Member member);
}
