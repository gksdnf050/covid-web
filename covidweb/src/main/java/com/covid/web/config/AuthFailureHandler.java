package com.covid.web.config;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Service
public class AuthFailureHandler implements  AuthenticationFailureHandler  {
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
       
        request.setAttribute("loginFailMsg", "만료된 계정입니다..");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/js/main/main.js");
		dispatcher.forward(request, response);
        
    }
}
