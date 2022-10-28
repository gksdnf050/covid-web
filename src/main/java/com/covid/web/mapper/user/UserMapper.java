package com.covid.web.mapper.user;

import com.covid.web.model.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
	User getUserByEmail(String email);
	int insertUser(User user);
}
