package com.alu.omc.oam.authorization;

import javax.servlet.http.HttpSession;

import net.sf.jpam.Pam;
import net.sf.jpam.PamReturnValue;

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
        if ("root".equalsIgnoreCase(user.getUsername())) {
                user.setPassword("");
                user.setReason("Wrong user name.");
                log.info("Deny root user login");
                return user;
        }
        Pam pam = new Pam();
        PamReturnValue ret = pam.authenticate(user.getUsername(), user.getPassword());
        log.info("verifyMsg" + ret.toString());
        if ((SystemUtils.IS_OS_WINDOWS || ret == PamReturnValue.PAM_SUCCESS) {
                user.setReason("user not found");
                user.setPassword("");
                String token = EncryptUtils.encryptMD5(user.getUsername() + "" + user.getPassword());
                user.setToken(token);
                user.setPassword("");
                user.setReason(null);
                session.setAttribute("token", token);
                log.info("user: " + user.getUsername() + "  -- login successful");
                return user;
        } else {
                user.setReason("wrong password");
                user.setPassword("");
                return user;
        }
}


	private static Logger log = LoggerFactory.getLogger(AuthController.class);

}
