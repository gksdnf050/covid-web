package com.covid.web.model.entity;

import lombok.*;

import java.util.Date;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class User {
    private int id;
    private String name;
    private String password;
    private String email;
    private Date createDate;
    private Date modifyDate;

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }
}