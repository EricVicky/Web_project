package com.alu.omc.oam.authorization;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.jpam.Pam;
import net.sf.jpam.PamReturnValue;

import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alu.omc.oam.util.CommandProtype;
import com.alu.omc.oam.util.CommandResult;
import com.alu.omc.oam.util.EncryptUtils;
import com.alu.omc.oam.util.ICommandExec;

@Controller("authController")
public class AuthController {
    final String checkLockedUser = "checkLockedUser";
    @Resource
    private  CommandProtype commandProtype;
    
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public @ResponseBody
    UserAccount login(@RequestBody final UserAccount user, HttpSession session) {
        if (SystemUtils.IS_OS_WINDOWS ) {
                user.setReason("");
                user.setPassword("");
                String token = EncryptUtils.encryptMD5(user.getUsername() + "" + user.getPassword());
                user.setToken(token);
                user.setPassword("");
                user.setReason(null);
                session.setAttribute("token", token);
                log.info("user: " + user.getUsername() + "  -- login successful");
                return user;
        }

        if ("root".equalsIgnoreCase(user.getUsername())) {
                user.setPassword("");
                user.setReason("Wrong user name.");
                log.info("Deny root user login");
                return user;
        }
        Pam pam = new Pam();
        PamReturnValue ret = pam.authenticate(user.getUsername(), user.getPassword());
        log.info("verifyMsg" + ret.toString());
        if (ret == PamReturnValue.PAM_SUCCESS)
        {
            if (isLocked(user.getUsername()))
            {
                user.setReason("user is locked");
                user.setPassword("");
                return user;
            }
            user.setReason("");
            user.setPassword("");
            String token = EncryptUtils.encryptMD5(user.getUsername() + ""
                    + user.getPassword());
            user.setToken(token);
            user.setPassword("");
            user.setReason(null);
            session.setAttribute("token", token);
            log.info("user: " + user.getUsername() + "  -- login successful");
            return user;
        }
        else
        {
            user.setReason("invalid user or password");
            user.setPassword("");
            return user;
        }
}
	@RequestMapping(value="/logout",  method=RequestMethod.DELETE)
	public @ResponseBody
    void logout( HttpSession session) {
	    session.invalidate();
	}
	
	boolean isLocked(String user){
	    boolean locked = false;
	    ICommandExec comamnda = commandProtype.create("checkLockedUser.sh " + user);
	    
	    try
        {
	        CommandResult res = comamnda.execute();
	        if(res.getExitValue() != 0){
	            locked = true;
	        }
        }
        catch (IOException | InterruptedException e)
        {
           locked =true; 
        }
	    return locked;
	}

	private static Logger log = LoggerFactory.getLogger(AuthController.class);

}
