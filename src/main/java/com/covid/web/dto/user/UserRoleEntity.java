package com.covid.web.dto.user;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleEntity {
	private String userLoginId;
	private String roleName;
}
