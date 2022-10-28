package com.covid.web.service;

import com.covid.web.model.entity.User;
import com.covid.web.model.entity.UserEntity;
import com.covid.web.model.entity.UserRoleEntity;

import java.util.List;

public interface UserService {
    void signUp(User user);
    UserEntity getUser(String loginUserId);
    List<UserRoleEntity> getUserRoles(String loginUserId);
}
