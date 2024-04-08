package com.xyz.config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.xyz.Entity.User;

public class CustomUser implements UserDetails {

	private User ub;
	public CustomUser(User ub) {
		this.ub=ub;
	}
	
	
	
	public User getUb() {
		return ub;
	}



	public void setUb(User ub) {
		this.ub = ub;
	}



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		SimpleGrantedAuthority sg = new SimpleGrantedAuthority(ub.getRole());
		return Arrays.asList(sg);
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return ub.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return ub.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return ub.isIsaccountnonlocaked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return ub.isEnable();
	}

}
