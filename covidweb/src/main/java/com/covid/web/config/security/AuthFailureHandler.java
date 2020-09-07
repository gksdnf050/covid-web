package com.covid.web.config.security;

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
       
    	//request.setAttribute("loginFailMsg", "로그인실패");
       //request.getRequestDispatcher("/loginerror").forward(request, response);
        //request.getSession().setAttribute("loginFailMsg", "만료된 계정입니다..");
    	response.sendRedirect("/login?error=" + exception.getMessage());

    }
}
