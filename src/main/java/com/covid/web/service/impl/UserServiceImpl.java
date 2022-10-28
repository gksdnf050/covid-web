package com.covid.web.service.impl;

import com.covid.web.model.entity.User;
import com.covid.web.model.transfer.request.SignupRequest;
import com.covid.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.covid.web.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	public void signUp(SignupRequest signupRequest) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // TODO bean

		User user = User.builder()
				.username(signupRequest.getUsername())
				.email(signupRequest.getEmail())
				.password(passwordEncoder.encode(signupRequest.getPassword()))
				.build();

		userRepository.save(user);
	}

	@Override
	public User getUser(String username) {
		return  userRepository.findByUsername(username);
	}
}
