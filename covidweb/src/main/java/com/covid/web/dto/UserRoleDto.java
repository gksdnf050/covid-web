package com.covid.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRoleDto {
	private int id;
	private int userId;
	private String roleName;

	public UserRoleDto(int userId, String roleName) {
		this.userId = userId;
		this.roleName = roleName;
	}
}