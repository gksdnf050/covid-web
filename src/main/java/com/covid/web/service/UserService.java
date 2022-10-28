package com.covid.web.service;

import com.covid.web.model.entity.User;
import com.covid.web.model.transfer.request.SignupRequest;

public interface UserService {
    void signUp(SignupRequest signupRequest);
    User getUser(String username);
}
