package com.covid.web.repository;

import com.covid.web.model.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
	User getUserByEmail(String email);
	int insertUser(User user);
}
