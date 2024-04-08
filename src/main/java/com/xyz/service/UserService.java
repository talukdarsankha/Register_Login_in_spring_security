package com.xyz.service;

import org.springframework.stereotype.Component;

import com.xyz.Entity.User;


public interface UserService {

	public User saveUser(User us,String url);
	
	public void removeSessoin();
	
	public void sendEmail(User u,String url);
	public boolean verifyAccount(String VerificationCode);
	
	public void increaseAttempt(User uo);
	public void resetAttempt(String email);
	public void lock(User us);
	public boolean unlockAccountTimeExpried(User uob);
}
