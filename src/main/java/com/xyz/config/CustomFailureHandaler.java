package com.xyz.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.xyz.Entity.User;
import com.xyz.repository.UserRepo;
import com.xyz.service.UserService;
import com.xyz.service.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomFailureHandaler extends SimpleUrlAuthenticationFailureHandler {

	@Autowired
	UserService userSer;
	
	@Autowired
	private UserRepo ur;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		String email= request.getParameter("username");
		User us= ur.findByEmail(email);
		
		if(us!=null) {
			if(us.isEnable()) {
				if(us.isIsaccountnonlocaked()) {
					if(us.getFailedAttempt()<UserServiceImpl.attempt_time) {
						userSer.increaseAttempt(us);
					}
					else {
						userSer.lock(us);
					}
				}
				else if(!us.isIsaccountnonlocaked()){
					if(userSer.unlockAccountTimeExpried(us)) {
						exception=new LockedException("Account unlocked Try again");
					}
					else {
						exception = new LockedException("Account lock for 5 Second");
					}
				}
			}else {
				exception = new LockedException("Unverified please verify yout account");
			}
			
		}
		else {
			exception = new LockedException("Account is inactive ...verify account");
		}
		super.setDefaultFailureUrl("/userlogin?error");
		super.onAuthenticationFailure(request, response, exception);
	}

	
	
	
}
