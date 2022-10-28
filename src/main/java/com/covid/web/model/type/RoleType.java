package com.covid.web.model.type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.LinkedHashSet;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
public enum RoleType implements Code<String> {
    USER("USER", new LinkedHashSet<>(AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"))),
    ;

    @Getter
    String code;

    @Getter
    Set<GrantedAuthority> roles;

    RoleType(String code, Set<GrantedAuthority> roles) {
        this.code = code;
        this.roles = roles;
    }
}
