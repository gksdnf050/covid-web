package com.covid.web.model.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sample_mt")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Sample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(length = 20, nullable = false)
    String name;

    @Column
    Integer age;

    @Column(length = 255)
    String address;
}
