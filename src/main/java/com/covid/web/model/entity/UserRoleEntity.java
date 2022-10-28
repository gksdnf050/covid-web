package com.covid.web.model.entity;

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
