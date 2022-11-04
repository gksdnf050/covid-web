package com.covid.web.config.security;

import com.covid.web.model.entity.User;
import com.covid.web.model.type.RoleType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class CustomUserDetails implements UserDetails {
	private Collection<? extends GrantedAuthority> authorities;
	private String username;
	private String password;
	private boolean isEnabled;
	private boolean isAccountNonExpired;
	private boolean isAccountNonLocked;
	private boolean isCredentialsNonExpired;

	private CustomUserDetails(User user) {
		this.authorities = RoleType.USER.getRoles();
		this.username = user.getUsername();
		this.password = user.getPassword();
	}
	public static UserDetails create(User user) {
		return new CustomUserDetails(user);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
