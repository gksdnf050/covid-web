package com.covid.web.exception;

import com.covid.web.exception.type.ErrorMessageType;
import com.covid.web.model.transfer.common.ResponseBase;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/error")
public class RestErrorController implements ErrorController {

    @RequestMapping
    public ResponseEntity handleError(HttpServletRequest request) {
        Object obj = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        log.error("Error {}", request );

        if(null == obj) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseBase.of(false, new Error(ErrorMessageType.UNKNOWN), null));
        }

        HttpStatus status = HttpStatus.resolve(Integer.valueOf(obj.toString()));

        return ResponseEntity.status(status).body(getResponse(status));
    }

    private ResponseBase getResponse(HttpStatus status) {
        switch (status) {
            case NOT_FOUND:
                return ResponseBase.of(false, new Error(ErrorMessageType.NOT_FOUND), null);
//            case UNAUTHORIZED:
//                return ResponseBase.of(false, new Error(ErrorMessageType.UNAUTHORIZED), null);
//            case FORBIDDEN:
//            case BAD_REQUEST:
//                return ResponseBase.of(false, new Error(ErrorMessageType.INVALID_REQUEST), null);
            default:
                return ResponseBase.of(false, new Error(ErrorMessageType.UNKNOWN), null);
        }
    }
}