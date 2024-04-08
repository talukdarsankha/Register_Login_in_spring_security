package com.xyz.config;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.xyz.Entity.User;
import com.xyz.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthDEfSuccessHandaler implements AuthenticationSuccessHandler {

	@Autowired
	private UserService userv;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		Set<String> role= AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		
		CustomUser cus= (CustomUser) authentication.getPrincipal();
		User uo= cus.getUb();
		if(uo!=null) {
			userv.resetAttempt(uo.getEmail());
		}
		
		
		
		if(role.contains("ROLE_ADMIN")) {
			response.sendRedirect("/admin/profile");
		}else {
			response.sendRedirect("/user/profile");
		}
		
		
	}

}
