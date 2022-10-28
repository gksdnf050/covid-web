package com.covid.web.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRole {
	private int id;
	private int userId;
	private String roleName;

	public UserRole(int userId, String roleName) {
		this.userId = userId;
		this.roleName = roleName;
	}
}