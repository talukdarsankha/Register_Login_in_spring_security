package com.xyz.controller;

import java.net.URI;
import java.net.http.HttpRequest;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.xyz.Entity.User;
import com.xyz.repository.UserRepo;
import com.xyz.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	private UserService us;
	
	@Autowired
	private UserRepo uur;
	
	@GetMapping(path = "/")
	public String index() {
		return "index";
	}
	
	@GetMapping(path = "/home")
	public String home() {
		return "home";
	}
	
	@GetMapping(path = "/register")
	public String register() {
		return "register";
	}
	
	@GetMapping(path =  "/userlogin")
	public String login() {
		return "login";
	}
	
//	@GetMapping(path = "/user/profile")
//	public String profile(Principal p,Model m) {
//		String email= p.getName();
//		User uob= uur.findByEmail(email);
//		m.addAttribute("us", uob);
//		
//		return "profile";
//	}
	
	@ModelAttribute
	public void commnVar(Principal p, Model m) {
		if(p!=null) {
		  String emi=	p.getName();
		   User sob= uur.findByEmail(emi);
		   m.addAttribute("uv", sob);
		}
	}
	
	
	@PostMapping(path = "/registerUser")
	public String regis(@ModelAttribute User uob,HttpSession hs,HttpServletRequest requ) {
		System.out.println(uob);
		String url=requ.getRequestURL().toString();  //http://localhost:8080/registerUser
		System.out.println(url);
		url=url.replace(requ.getServletPath(),"");
		System.out.println(url);
		
		  User ub= us.saveUser(uob,url);
		  if(ub==null) {
			  System.out.println("error occured");
			  hs.setAttribute("ermsg", "error occured");
		  }else {
			  System.out.println("Register occured");
			  hs.setAttribute("msg", "Register occured");
		  }
		 
		return "redirect:/register";
		
	}
	
	
	@GetMapping(path = "/verify")
	public String verifyAccount(@Param("code") String code,Model m) {
		boolean f= us.verifyAccount(code);
		if(f) {
			m.addAttribute("msg", "verified");
		}else {
			m.addAttribute("msg", "unverified");
		}
		return "message";
	}
	
	
}
