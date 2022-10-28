package com.covid.web.exception;

import com.covid.web.exception.type.ErrorMessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Error {
    private Integer code;
    private String message;

    public Error(ErrorMessageType type) {
        this.code = type.getCode();
        this.message = type.getMessage();
    }
}