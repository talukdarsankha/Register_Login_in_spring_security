package com.xyz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.xyz.Entity.User;
import com.xyz.repository.UserRepo;

@Component
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepo ur;
	
	@Override
	public UserDetails loadUserByUsername(String ss) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User us= ur.findByEmail(ss);
		if(us==null) {
			throw new UsernameNotFoundException("User Not Found");
		}
		else {
			return new CustomUser(us);
		}
	}

}
