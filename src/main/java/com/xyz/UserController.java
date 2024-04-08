package com.xyz;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xyz.Entity.User;
import com.xyz.repository.UserRepo;

@Controller
@RequestMapping(path = "/user")
public class UserController {

	@Autowired
	UserRepo ur;
	
	@ModelAttribute
	public void commonvar(Model m,Principal p) {
		String em= p.getName();
		User uob= ur.findByEmail(em);
		m.addAttribute("at", uob);
	}
	
	@RequestMapping(path = "/profile")
	public String userProfile() {
		return "profile";
	}
	
}
