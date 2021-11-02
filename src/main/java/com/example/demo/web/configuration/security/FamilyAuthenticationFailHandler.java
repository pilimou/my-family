package com.example.demo.web.configuration.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class FamilyAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		HttpSession session = request.getSession();
		String errorMsg = "帳號或密碼錯誤";
		session.setAttribute("error", errorMsg);
		String ip = request.getRemoteAddr();
		super.setDefaultFailureUrl("/sys/sign-in");
		super.onAuthenticationFailure(request, response, exception);
	}
}
