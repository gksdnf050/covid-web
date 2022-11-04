package com.covid.web.repository;

import com.covid.web.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserRepository {
	User findByUsername(String username);
	int insertUser(User user);
}
