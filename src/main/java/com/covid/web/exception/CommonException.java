package com.covid.web.exception;

import com.covid.web.exception.type.ErrorMessageType;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

public class CommonException extends RuntimeException{
    private String code;
    private String message;
    private String[] args;
    private HttpStatus status;
    private ErrorMessageType type;

    public CommonException(ErrorMessageType errorMessageType) {
        super();
        this.type = errorMessageType;
        this.code = errorMessageType.getCode().toString();
        this.message = errorMessageType.getMessage();
        this.status = errorMessageType.getStatus();
    }

    public CommonException(String code) {
        super();
        this.code = code;
        this.message = "";
    }

    public CommonException(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public CommonException(String code, String message, String[] args) {
        super();
        this.code = code;
        this.message = message;
        this.args = args;
    }

    public Integer getCodeNum() {
        if (!StringUtils.hasText(this.code))
            return 0;

        Integer lastIndex = this.code.lastIndexOf(".");
        String realCode;
        if(lastIndex > 0)
            realCode = this.code.substring(lastIndex + 1);
        else
            realCode = this.code;

        if (!StringUtils.hasText(realCode))
            return 0;
        else
            return Integer.parseInt(realCode);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public ErrorMessageType getErrorMessageType() {
        return this.type;
    }
}