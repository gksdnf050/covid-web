package com.covid.web.model.type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public enum SampleType implements Code<String> {
    EMAIL("EMAIL", "email"),
    PHONE("PHONE", "phone");

    @Getter
    String code;

    @Getter
    String name;

    SampleType(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
