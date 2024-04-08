package com.xyz.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xyz.Entity.User;
import com.xyz.repository.UserRepo;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Component
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo ur;
	
	@Autowired
	private JavaMailSender mailSender;
	
	
	@Autowired
	private BCryptPasswordEncoder bcp;
	
	@Override
	public User saveUser(User us,String url) {
		// TODO Auto-generated method stub
		String pass= bcp.encode(us.getPassword());
		us.setPassword(pass);
		us.setRole("ROLE_USER");
		us.setEnable(false);
		us.setVerificationCode(UUID.randomUUID().toString());
		
		us.setIsaccountnonlocaked(true);
		us.setFailedAttempt(0);
		us.setLocktime(null);
		
		User uo= ur.save(us);
		if(uo!=null) {
			sendEmail(uo, url);
		}
		return uo;
	}

	@Override
	public void removeSessoin() {
	  HttpSession hs= ( (ServletRequestAttributes)(RequestContextHolder.getRequestAttributes()))
	   .getRequest().getSession();
	  hs.removeAttribute("msg");
	}

	@Override
	public void sendEmail(User u, String url) {
		// TODO Auto-generated method stub
		
		String from= "sagarayantalukdar@gmail.com";
		String to = u.getEmail();
		String subject="Accout Verification";
		String content = "Dear [[name]],<br>"+"please click the link bellow to verify your registration:<br>"
		+"<h3> <a href=\"[[URL]]\" target=\"_self\"> VERIFY </a> </h3>" +"Thank you,<br>"+"Becoder";
		
		try {
			MimeMessage mm = mailSender.createMimeMessage();
			MimeMessageHelper mmh = new MimeMessageHelper(mm);
			
			mmh.setFrom(from,"sankha");
			mmh.setTo(to);
			mmh.setSubject(subject);
			
			content= content.replace("[[name]]", u.getName()); 
			String siteUrl = url+"/verify?code="+u.getVerificationCode();
			content=content.replace("[[URL]]", siteUrl);
			mmh.setText(content, true);
			mailSender.send(mm);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}

	@Override
	public boolean verifyAccount(String VerificationCode) {
		// TODO Auto-generated method stub
		User uob= ur.findByVerificationCode(VerificationCode);
		if(uob!=null) {
			uob.setEnable(true);
			uob.setVerificationCode(null);
			ur.save(uob);
			return true;
		}else {
			return false;
		}
		
	}

	@Override
	public void increaseAttempt(User uo) {
		// TODO Auto-generated method stub
		int attempt = uo.getFailedAttempt()+1;
		ur.updateFailedAttempt(attempt, uo.getEmail());
	}

	@Override
	public void resetAttempt(String email) {
		// TODO Auto-generated method stub
		ur.updateFailedAttempt(0, email);
	}

	@Override
	public void lock(User us) {
		// TODO Auto-generated method stub
		us.setIsaccountnonlocaked(false);
		us.setLocktime(new Date());
		ur.save(us);
	}

	public static final long lock_Duration = 60000;
	public static final int attempt_time=3;
	
	@Override
	public boolean unlockAccountTimeExpried(User uob) {
		// TODO Auto-generated method stub
		long lockTimeinMillis = uob.getLocktime().getTime();
		long currentTime=System.currentTimeMillis();
		if(lockTimeinMillis+lock_Duration<currentTime) {
			uob.setIsaccountnonlocaked(true);
			uob.setFailedAttempt(0);
			uob.setLocktime(null);
			ur.save(uob);
			return true;
		}
		return false;
	}

	

	
	
	
	
	

}
