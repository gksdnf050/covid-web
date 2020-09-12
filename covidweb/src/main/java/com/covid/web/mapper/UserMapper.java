package com.covid.web.mapper;

import com.covid.web.dto.UserDto;

public interface UserMapper {
	public UserDto getUserByEmail(String email);
	public int insertUser(UserDto userDto);
}
