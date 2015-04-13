package com.alu.omc.oam.authorization;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alu.omc.oam.util.EncryptUtils;

@Controller("authController")
public class AuthController {

	@RequestMapping("/login")
	public @ResponseBody
	UserAccount login(@RequestBody final UserAccount user, HttpSession session) {

//		Pam pam = new Pam();
//		String verifyMsg = pam.authenticate(user.getUsername(), user.getPassword()).toString();
//		if (verifyMsg.contains("underlying")) {
//			user.setReason("user not found");
//			return user;
//		} else if (verifyMsg.contains("failure")) {
//			user.setReason("wrong password");
//			return user;
//		}
		
		user.setReason(null);
		

		String token = EncryptUtils.encryptMD5(user.getUsername() + "" + user.getPassword());
		user.setToken(token);
		user.setPassword("");
		session.setAttribute("token", token);

		log.info("user: " + user.getUsername() + " password: " + user.getUsername() + " -- successful");

		return user;
	}

	private static Logger log = LoggerFactory.getLogger(AuthController.class);

}
