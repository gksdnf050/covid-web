package com.covid.web.config.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class AuthFailureHandler implements  AuthenticationFailureHandler  {
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        if(exception instanceof BadCredentialsException) {
            String loginFailMsg = "가입하지 않은 아이디이거나, 잘못된 비밀번호입니다.";

            FlashMap flashMap = new FlashMap(); // flashMap 생성 (https://stackoverflow.com/questions/23844546/flash-attribute-in-custom-authenticationfailurehandler 참조)
            flashMap.put("loginFailMsg", loginFailMsg); // flashMap에 로그인 실패 메시지 담음.
            
            FlashMapManager flashMapManager = new SessionFlashMapManager();
            flashMapManager.saveOutputFlashMap(flashMap, request, response);
        }

    	response.sendRedirect("/login");    // 로그인 페이지로 리다이렉트
    }
}
