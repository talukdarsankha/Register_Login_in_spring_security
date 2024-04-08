package com.xyz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private AuthDEfSuccessHandaler ash;
	
	@Autowired
	private CustomFailureHandaler cfh;
	
	@Bean
	public BCryptPasswordEncoder getBCP() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService getUDS() {
		return new CustomUserDetailService();
	}
	
	@Bean
	public DaoAuthenticationProvider getDAp() {
		DaoAuthenticationProvider dap = new DaoAuthenticationProvider();
		dap.setUserDetailsService(getUDS());
		dap.setPasswordEncoder(getBCP());
		return dap;
	}
	
	@Bean
	public SecurityFilterChain getSEc(HttpSecurity htts) throws Exception {
//		htts.csrf().disable()
//		.authorizeHttpRequests().requestMatchers("/","/register","/userlogin","/registerUser")
//		.permitAll().requestMatchers("/user/**").authenticated()
//		.and().formLogin().loginPage("/userlogin")
//		.loginProcessingUrl("/logUser").defaultSuccessUrl("/user/profile")
//		.permitAll();
//		return htts.build();
		
		
	   htts.csrf().disable().authorizeHttpRequests()
	   .requestMatchers("/user/**").hasRole("USER")
	   .requestMatchers("/admin/**").hasRole("ADMIN")
	   .requestMatchers("/**").permitAll().and()
	   .formLogin().loginPage("/userlogin").loginProcessingUrl("/logUser")
	   .failureHandler(cfh)
	   .successHandler(ash)
	   .and()
	   .logout().permitAll();
	   
	   return htts.build();
	   
		
		
	}
	
}
