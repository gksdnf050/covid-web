package com.covid.web.dto.user;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    private String loginUserId;
    private String password;
}
