package com.xyz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.xyz.Entity.User;

public interface UserRepo  extends JpaRepository<User, Integer>{

	public User findByEmail(String em);
	
	public User findByVerificationCode(String v);
	
	@Query("update User u set u.failedAttempt=?1 Where email =?2")
	@Modifying
	public void updateFailedAttempt(int attempt,String email);
	
}
