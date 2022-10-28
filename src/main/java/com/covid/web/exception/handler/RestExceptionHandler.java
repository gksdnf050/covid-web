package com.covid.web.exception.handler;

import com.covid.web.exception.CommonException;
import com.covid.web.exception.type.ErrorMessageType;
import com.covid.web.model.transfer.common.ResponseBase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.InvalidParameterException;
import com.covid.web.exception.Error;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(annotations = RestController.class)
@RequiredArgsConstructor
public class RestExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ResponseBase> handleCommonException(CommonException ex) {
        log.error("Common Exception -> {} ", ex.getMessage(), ex);
        return ResponseBase.fail(new Error(ex.getErrorMessageType()), ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseBase> handleException(Exception ex) {
        log.error("Exception -> {}", ex.getMessage(), ex);
        return ResponseBase.fail(new Error(ErrorMessageType.UNKNOWN), ErrorMessageType.UNKNOWN.getStatus());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ResponseBase> handleBindException(BindException ex) {
        log.error("Bind Exception -> {} ", ex.getMessage(), ex);
        return ResponseBase.fail(new Error(ErrorMessageType.INVALID_PARAM), ErrorMessageType.INVALID_PARAM.getStatus());
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ResponseBase> handleInvalidParameterException(InvalidParameterException ex) {
        log.error("InvalidParameterException: {}", ex.getMessage(), ex);
        return ResponseBase.fail(new Error(ErrorMessageType.INVALID_PARAM), ErrorMessageType.INVALID_PARAM.getStatus());
    }
}
