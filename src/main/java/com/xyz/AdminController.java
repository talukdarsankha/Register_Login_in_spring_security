package com.xyz;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xyz.Entity.User;
import com.xyz.repository.UserRepo;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

	
	@Autowired
	UserRepo ur;
	
	@ModelAttribute
	public void commVar(Principal p,Model m) {
		String em = p.getName();
		User uob= ur.findByEmail(em);
		m.addAttribute("att", uob);
	}
	
	@GetMapping(path = "/profile")
	public String profile() {
		return "admin_profile";
	}
	
	
}
