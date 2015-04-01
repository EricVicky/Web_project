package com.alu.omc.oam.authorization;

import net.sf.jpam.Pam;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("authController")
public class AuthController {

	@RequestMapping("/auth/v")
	public @ResponseBody
	String login(String userName, String userPwd) {

		Pam pam = new Pam();
		String verifyMsg = pam.authenticate(userName, userPwd).toString();
		String result = "";
		if (verifyMsg.contains("underlying")) {
			result = "user not found";
		} else if (verifyMsg.contains("failure")) {
			result = "wrong password";
		}

		logger.info("user: " + userName + " password: " + userPwd + " -- successful");

		return result;
	}

	private static final Logger logger = Logger.getLogger(AuthController.class);

}
