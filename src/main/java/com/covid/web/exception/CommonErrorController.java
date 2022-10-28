package com.covid.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("/error")
public class CommonErrorController implements ErrorController {
    private final String ERROR_NOT_FOUND = "exception/not_found";
    private final String ERROR_BAD_REQUEST = "exception/bad_request";
    private final String ERROR_FORBIDDEN = "exception/forbidden";
    private final String ERROR_INTERNAL_SERVER = "exception/internal_server_error";
    private final String ERROR_UNAUTHORIZED = "exception/unauthorized";
    private final String ERROR_UNKNOWN = "exception/unknown";

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(null == status) {
            return getDefaultPage();
        }

        return getErrorPage(HttpStatus.valueOf(Integer.valueOf(status.toString())));
    }

    private ModelAndView getErrorPage(HttpStatus status) {
        ModelAndView mnv = new ModelAndView();
        mnv.addObject("title", status.getReasonPhrase());
        mnv.addObject("status", status.value());

        switch (status) {
            case NOT_FOUND:
                mnv.setViewName(ERROR_NOT_FOUND);
                return mnv;
            case BAD_REQUEST:
                mnv.setViewName(ERROR_BAD_REQUEST);
                return mnv;
            case FORBIDDEN:
                mnv.setViewName(ERROR_FORBIDDEN);
                return mnv;
            case INTERNAL_SERVER_ERROR:
                mnv.setViewName(ERROR_INTERNAL_SERVER);
                return mnv;
            case UNAUTHORIZED:
                mnv.setViewName(ERROR_UNAUTHORIZED);
                return mnv;
            default:
                return getDefaultPage();
        }
    }

    private ModelAndView getDefaultPage() {
        ModelAndView mnv = new ModelAndView();
        mnv.addObject("title", "Oops");
        mnv.addObject("status", "Unknown");
        mnv.setViewName(ERROR_UNKNOWN);
        return mnv;
    }
}