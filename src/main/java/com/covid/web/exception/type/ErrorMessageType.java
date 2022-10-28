package com.covid.web.exception.type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.function.Predicate;

@FieldDefaults(level = AccessLevel.PACKAGE)
public enum ErrorMessageType {
    UNKNOWN(-999, "Unknown", HttpStatus.BAD_REQUEST),
    MAINTENANCE(-1, "Maintenance", HttpStatus.SERVICE_UNAVAILABLE),

    INTERNAL_SERVER(1000, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    INITIALIZE_FAIL(1001, "Failed to initialize", HttpStatus.INTERNAL_SERVER_ERROR),

    INVALID_PARAM(1050, "Invalid parameter", HttpStatus.BAD_REQUEST),
    INVALID_REQUEST(1051, "Invalid request", HttpStatus.BAD_REQUEST),

    NOT_FOUND(1100, "Not found information", HttpStatus.NOT_FOUND),

    UNAUTHORIZED(1150, "Unauthorized", HttpStatus.UNAUTHORIZED),

    FORBIDDEN(1200,"Forbidden", HttpStatus.FORBIDDEN),
    ;

    @Getter
    Integer code;

    @Getter
    String message;

    @Getter
    HttpStatus status;

    ErrorMessageType(Integer code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    ErrorMessageType find(Predicate<ErrorMessageType> predicate) {
        return Arrays.stream(values())
                .filter(predicate)
                .findAny()
                .orElse(UNKNOWN);
    }
}
