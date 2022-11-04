package com.covid.web.model.entity;

import com.covid.web.model.type.RoleType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    Long id;
    String username;
    String password;
    String email;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}